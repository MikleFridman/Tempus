package il.fridman.tempus.timekeeping.repository;

import il.fridman.tempus.timekeeping.entity.Calendar;
import il.fridman.tempus.timekeeping.enums.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @Query("SELECT c FROM Calendar c WHERE c.holiday = :holiday AND c.year = :year")
    LocalDate findHolidayByYear(Holiday holiday, int year);

    @Query("SELECT c FROM Calendar c WHERE c.date = :date")
    List<Holiday> findByDate(LocalDate date);
}