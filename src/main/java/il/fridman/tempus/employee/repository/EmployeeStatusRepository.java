package il.fridman.tempus.employee.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.entity.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeStatusRepository extends JpaRepository<EmployeeStatus, Long> {

    @Query("SELECT es.employee FROM EmployeeStatus es WHERE es.number = :number")
    Optional<Employee> findEmployeeByNumber(int number);

    @Query("SELECT es FROM EmployeeStatus es WHERE es.employee = :employee " +
           "AND (es.validityPeriod.startDate >= :date AND es.validityPeriod.expiryDate <= :date OR es.validityPeriod.expiryDate IS NULL)")
    Optional<EmployeeStatus> findByDate(Employee employee, LocalDate date);
}
