package il.fridman.tempus.salary.mapper;

import il.fridman.tempus.salary.dto.CalculationDTO;
import il.fridman.tempus.salary.entity.Calculation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CalculationMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(target = "employeeName", expression = "java(calculation.getEmployee().getFullName())")
    @Mapping(source = "payroll.id", target = "payrollId")
    @Mapping(source = "payroll.name", target = "payrollName")
    CalculationDTO toDTO(Calculation calculation);
}
