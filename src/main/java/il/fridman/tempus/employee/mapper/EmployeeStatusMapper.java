package il.fridman.tempus.employee.mapper;

import il.fridman.tempus.employee.dto.EmployeeStatusDTO;
import il.fridman.tempus.employee.entity.EmployeeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeStatusMapper {

    @Mapping(source = "validityPeriod.startDate", target = "startDate")
    @Mapping(source = "validityPeriod.expiryDate", target = "expiryDate")
    EmployeeStatusDTO toDTO(EmployeeStatus employeesStatus);
}
