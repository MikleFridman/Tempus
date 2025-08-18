package il.fridman.tempus.salary.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.salary.entity.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.List;

public interface CalculationRepository extends JpaRepository<Calculation, Long> {

    @Query("SELECT c FROM Calculation c WHERE c.employee = :employee AND c.payPeriod = :payPeriod")
    List<Calculation> findByEmployeeAndPayPeriod(Employee employee, YearMonth payPeriod);
}
