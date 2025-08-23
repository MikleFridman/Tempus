package il.fridman.tempus.salary.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.redis.RedisService;
import il.fridman.tempus.salary.entity.Calculation;
import il.fridman.tempus.salary.entity.Earning;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import il.fridman.tempus.timekeeping.enums.DayType;
import il.fridman.tempus.salary.enums.PayrollBasis;
import il.fridman.tempus.company.enums.SettingParameter;
import il.fridman.tempus.timekeeping.enums.Timecode;
import il.fridman.tempus.timekeeping.service.CalendarService;
import il.fridman.tempus.company.service.SettingService;
import il.fridman.tempus.timekeeping.service.TimesheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@Loggable
@RequiredArgsConstructor
public class EarningCalculationService {

    private final TimesheetService timesheetService;
    private final CalendarService calendarService;
    private final SettingService settingService;
    private final CalculationService calculationService;
    private final RedisService redisService;

    @Transactional
    public Calculation calculate(Employee employee, Earning earning, YearMonth payPeriod) {
        double amount = 0.00;
        Calculation calculation = new Calculation(payPeriod, earning, employee);
        String redisKey = "salary:" + employee.getId() + ":" + payPeriod.toString();

        if (earning.getBasis() == PayrollBasis.FIXED) {
            amount = 1.00;
            calculation.setLog("Payroll basis", "Fixed earning");
            calculation.setLog("Payroll amount", "1.00");
        } else if (earning.getBasis() == PayrollBasis.WORKING_HOURS) {
            calculation.setLog("Payroll basis", "Calculating based on working hours");

            double totalHoursActual = getActualHours(employee, payPeriod, redisKey);
            double totalHoursStandard = getStandardHours(employee, payPeriod, redisKey);
            calculation.setLog("Total actual hours", String.valueOf(totalHoursActual));
            calculation.setLog("Total standard hours", String.valueOf(totalHoursStandard));

            amount = totalHoursActual / totalHoursStandard;
            amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            amount = amount > 1 ? 1 : amount;
        }
        calculation.setLog("Payroll amount", String.valueOf(amount));
        calculation.setAmount(amount);
        calculation.setSum(amount * earning.getRate());

        return calculationService.save(calculation);
    }

    public double getActualHours(Employee employee, YearMonth payPeriod, String redisKey) {

        Object totalHoursActualFromCache = redisService.getHashValue(redisKey, "actualHours");
        if (totalHoursActualFromCache == null) {
            totalHoursActualFromCache = 0.00;
        }
        double totalHoursActual = (double) totalHoursActualFromCache;
        if (totalHoursActual > 0.00) {
            return totalHoursActual;
        }

        for (Timesheet timesheet : timesheetService.getByTimecodeAndPeriod(employee, Timecode.WORK,
                payPeriod.getMonthValue(), payPeriod.getYear())) {
            totalHoursActual += timesheet.getHours();
        }
        redisService.setHashValue(redisKey, "actualHours", totalHoursActual);
        return totalHoursActual;
    }

    public double getStandardHours(Employee employee, YearMonth payPeriod, String redisKey) {

        Object totalHoursStandardFromCache =  redisService.getHashValue(redisKey, "standardHours");
        if (totalHoursStandardFromCache == null) {
            totalHoursStandardFromCache = 0.00;
        }
        double totalHoursStandard = (double) totalHoursStandardFromCache;
        if (totalHoursStandard > 0.00) {
            return totalHoursStandard;
        }

        Map<String, Double> workingHoursPlan = settingService.getParameterValues(employee,
                SettingParameter.MIN_WORKING_HOURS,
                LocalDate.of(payPeriod.getYear(), payPeriod.getMonth(), 1));
        Map<LocalDate, Set<DayType>> daysOfMonth = calendarService.getDaysOfMonthWithTypes(payPeriod);

        for (LocalDate date : daysOfMonth.keySet()) {
            Set<DayType> types = daysOfMonth.get(date);
            String dayOfWeek = date.getDayOfWeek().toString();
            List<Double> quotes = new ArrayList<>();

            if (workingHoursPlan.containsKey(dayOfWeek)) {
                quotes.add(workingHoursPlan.get(dayOfWeek));
            }
            for (DayType type : types) {
                String key = type.name();
                if (workingHoursPlan.containsKey(key)) {
                    quotes.add(workingHoursPlan.get(key));
                }
                key = dayOfWeek + "_" + type.name();
                if (workingHoursPlan.containsKey(key)) {
                    quotes.add(workingHoursPlan.get(key));
                }
            }
            if (!quotes.isEmpty()) {
                System.out.println("Quotes for " + date + ": " + quotes);
                totalHoursStandard += Collections.min(quotes);
            }
        }
        redisService.setHashValue(redisKey, "standardHours", totalHoursStandard);
        return totalHoursStandard;
    }
}
