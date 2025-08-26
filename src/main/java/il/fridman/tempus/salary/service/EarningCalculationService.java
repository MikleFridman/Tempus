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
        double rate = 0.00;
        Calculation calculation = new Calculation(payPeriod, earning, employee);
        String redisKey = "salary:" + employee.getId() + ":" + payPeriod.toString();

        if (earning.getBasis() == PayrollBasis.FIXED) {
            amount = 1.00;
            rate = earning.getRate();
            calculation.setLog("Payroll basis", "Fixed earning");
            calculation.setLog("Payroll amount", "1.00");
        } else if (earning.getBasis() == PayrollBasis.WORKING_HOURS) {
            double actualHours = getActualHours(employee, payPeriod, redisKey);
            double standardHours = getStandardHours(employee, payPeriod, redisKey);
            calculation.setLog("Payroll basis", "Calculating based on working hours");
            calculation.setLog("Actual hours", String.valueOf(actualHours));
            calculation.setLog("Standard hours", String.valueOf(standardHours));

            amount = actualHours / standardHours;
            amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            amount = amount > 1 ? 1 : amount;
            rate = earning.getRate();

            if (actualHours > standardHours) {
                double overtimeHours = actualHours - standardHours;
                calculation.setLog("Overtime hours", String.valueOf(overtimeHours));
                redisService.setHashValue(redisKey, "overtimeHours", overtimeHours);
            }
        } else if (earning.getBasis() == PayrollBasis.OVERTIME_HOURS) {
            double overtimeHours = getOvertimeHours(employee, payPeriod, redisKey);
            calculation.setLog("Payroll basis", "Calculating based on overtime hours");
            calculation.setLog("Overtime hours", String.valueOf(overtimeHours));
            amount = overtimeHours;
            rate = getOvertimeRate(employee, payPeriod);
        }

        calculation.setLog("Payroll amount", String.valueOf(amount));
        calculation.setAmount(amount);
        calculation.setSum(amount * rate);

        return calculationService.save(calculation);
    }

    public double getActualHours(Employee employee, YearMonth payPeriod, String redisKey) {

        Object actualHoursFromCache = redisService.getHashValue(redisKey, "actualHours");
        if (actualHoursFromCache == null) {
            actualHoursFromCache = 0.00;
        }
        double actualHours = (double) actualHoursFromCache;
        if (actualHours > 0.00) {
            return actualHours;
        }

        for (Timesheet timesheet : timesheetService.getByTimecodeAndPeriod(employee, Timecode.WORK,
                payPeriod.getMonthValue(), payPeriod.getYear())) {
            actualHours += timesheet.getHours();
        }
        redisService.setHashValue(redisKey, "actualHours", actualHours);
        return actualHours;
    }

    public double getStandardHours(Employee employee, YearMonth payPeriod, String redisKey) {

        Object standardHoursFromCache =  redisService.getHashValue(redisKey, "standardHours");
        if (standardHoursFromCache == null) {
            standardHoursFromCache = 0.00;
        }
        double standardHours = (double) standardHoursFromCache;
        if (standardHours > 0.00) {
            return standardHours;
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
                standardHours += Collections.min(quotes);
            }
        }
        redisService.setHashValue(redisKey, "standardHours", standardHours);
        return standardHours;
    }

    public double getOvertimeHours(Employee employee, YearMonth payPeriod, String redisKey) {
        double overtimeHours;

        Object overtimeHoursFromCache = redisService.getHashValue(redisKey, "overtimeHours");
        if (overtimeHoursFromCache != null) {
            overtimeHours = (double) overtimeHoursFromCache;
            return overtimeHours;
        }

        double actualHours = getActualHours(employee, payPeriod, redisKey);
        double standardHours = getStandardHours(employee, payPeriod, redisKey);
        if (actualHours <= standardHours) {
            return 0.00;
        }
        overtimeHours = actualHours - standardHours;
        return overtimeHours;
    }

    public double getOvertimeRate(Employee employee, YearMonth payPeriod) {
        return settingService.getParameterValues(employee, SettingParameter.HOURLY_RATE,
                LocalDate.of(payPeriod.getYear(), payPeriod.getMonth(), 1))
                .getOrDefault(DayType.REGULAR.name(), 0.00);
    }
}
