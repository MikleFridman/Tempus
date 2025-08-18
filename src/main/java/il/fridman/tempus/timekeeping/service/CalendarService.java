package il.fridman.tempus.timekeeping.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.timekeeping.entity.Calendar;
import il.fridman.tempus.timekeeping.enums.DayType;
import il.fridman.tempus.timekeeping.enums.Holiday;
import il.fridman.tempus.timekeeping.repository.CalendarRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Loggable
@RequiredArgsConstructor
public class CalendarService implements EntityService<Calendar> {

    private final CalendarRepository calendarRepository;

    @Override
    @Transactional
    public Calendar save(Calendar entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Calendar entity cannot be null.");
        }
        return calendarRepository.save(entity);
    }

    @Override
    @Transactional
    public Calendar update(Calendar entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Calendar entity cannot be null.");
        }

        return calendarRepository.save(entity);
    }

    @Override
    public Calendar getById(Long id) {
        return calendarRepository.findById(id).orElse(null);
    }

    @Override
    public List<Calendar> getAll() {
        return calendarRepository.findAll();
    }

    public LocalDate getByYear(Holiday holiday, int year) {
        return calendarRepository.findHolidayByYear(holiday, year);
    }

    public List<Holiday> getByDate(LocalDate date) {
        return calendarRepository.findByDate(date);
    }

    public boolean isShabbat(LocalDate date) {
        return date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY;
    }

    public boolean isBeforeShabbat(LocalDate date) {
        return false;
    }

    public boolean isAfterShabbat(LocalDate date) {
        return false;
    }

    public boolean isHoliday(LocalDate date) {
        return false;
    }

    public boolean isBeforeHoliday(LocalDate date) {
        return isHoliday(date.plusDays(1));
    }

    public boolean isAfterHoliday(LocalDate date) {
        return isHoliday(date.minusDays(1));
    }

    public boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY ||
               date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY;
    }

    public boolean isBeforeWeekend(LocalDate date) {
        return false;
    }

    public boolean isAfterWeekend(LocalDate date) {
        return false;
    }

    public boolean isRegular(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.getDayOfWeek() == DayOfWeek.MONDAY ||
                date.getDayOfWeek() == DayOfWeek.TUESDAY ||
                date.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                date.getDayOfWeek() == DayOfWeek.THURSDAY;
    }

    public Set<DayType> getDayType(LocalDate date) {
        Set<DayType> dayTypes = new HashSet<>();
        if (isShabbat(date)) {
            dayTypes.add(DayType.SHABBAT);
        }
        if (isBeforeShabbat(date)) {
            dayTypes.add(DayType.BEFORE_SHABBAT);
        }
        if (isAfterShabbat(date)) {
            dayTypes.add(DayType.AFTER_SHABBAT);
        }
        if (isHoliday(date)) {
            dayTypes.add(DayType.HOLIDAY);
        }
        if (isBeforeHoliday(date)) {
            dayTypes.add(DayType.BEFORE_HOLIDAY);
        }
        if (isAfterHoliday(date)) {
            dayTypes.add(DayType.AFTER_HOLIDAY);
        }
        if (isWeekend(date)) {
            dayTypes.add(DayType.WEEKEND);
        }
        if (isBeforeWeekend(date)) {
            dayTypes.add(DayType.BEFORE_WEEKEND);
        }
        if (isAfterWeekend(date)) {
            dayTypes.add(DayType.AFTER_WEEKEND);
        }
        if (isRegular(date)) {
            dayTypes.add(DayType.REGULAR);
        }
        return dayTypes;
    }

    public Map<LocalDate, Set<DayType>> getDaysOfMonthWithTypes(YearMonth yearMonth) {
        Map<LocalDate, Set<DayType>> result = new java.util.HashMap<>();

        int lengthOfMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i <= lengthOfMonth; i++) {
            LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), i);
            result.put(date, getDayType(date));
        }

        return result;
    }
}