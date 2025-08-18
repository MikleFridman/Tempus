package il.fridman.tempus.timekeeping.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import il.fridman.tempus.timekeeping.enums.Timecode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    @Query("SELECT t FROM Timesheet t WHERE t.employee = :employee AND t.date BETWEEN :startDate AND :endDate")
    List<Timesheet> findByEmployeeAndDateRange(Employee employee, LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Timesheet t WHERE t.employee = :employee AND t.date = :date")
    List<Timesheet> findByEmployeeAndDate(Employee employee, LocalDate date);

    @Query( "SELECT t FROM Timesheet t WHERE t.employee = :employee AND t.month = :month AND t.year = :year")
    List<Timesheet> findByEmployeeAndMonthAndYear(Employee employee, int month, int year);

    @Query("SELECT t FROM Timesheet t WHERE t.employee = :employee AND t.timecode = :timecode" +
           " AND t.month = :month AND t.year = :year")
    List<Timesheet> findByEmployeeAndTimecodeAndMonthAndYear(Employee employee, Timecode timecode, int month, int year);
}