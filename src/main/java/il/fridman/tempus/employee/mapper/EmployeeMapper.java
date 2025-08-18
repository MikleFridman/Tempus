package il.fridman.tempus.employee.mapper;

import il.fridman.tempus.employee.dto.EmployeeDTO;
import il.fridman.tempus.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);
}
