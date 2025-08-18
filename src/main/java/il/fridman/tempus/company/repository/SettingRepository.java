package il.fridman.tempus.company.repository;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.company.entity.Setting;
import il.fridman.tempus.company.enums.SettingParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query("SELECT s FROM Setting s JOIN s.employees e WHERE s.parameter = :parameter AND e = :employee")
    List<Setting> findByParameterAndEmployee(SettingParameter parameter, Employee employee);
}
