package il.fridman.tempus.salary.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.salary.entity.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EarningRepository extends JpaRepository<Earning, Long> {

    @Query("SELECT e FROM Earning e WHERE e.employee = :employee")
    List<Earning> findAllByEmployee(Employee employee);
}
