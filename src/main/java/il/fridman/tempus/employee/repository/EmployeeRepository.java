package il.fridman.tempus.employee.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.enums.StatusForEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT es.employee from EmployeeStatus es where es.status = :status")
    List<Employee> getAllByStatus(StatusForEmployee status);
}
