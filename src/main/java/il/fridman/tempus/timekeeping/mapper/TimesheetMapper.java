package il.fridman.tempus.timekeeping.mapper;

import il.fridman.tempus.timekeeping.dto.TimesheetDTO;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimesheetMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    TimesheetDTO toDTO(Timesheet timesheet);

    @Mapping(source = "employee.id", target = "employeeId")
    List<TimesheetDTO> toDTOList(List<Timesheet> timesheets);
}
