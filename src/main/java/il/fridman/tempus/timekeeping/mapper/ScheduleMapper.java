package il.fridman.tempus.timekeeping.mapper;

import il.fridman.tempus.timekeeping.dto.ScheduleDTO;
import il.fridman.tempus.timekeeping.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "validityPeriod.startDate", target = "startDate")
    @Mapping(source = "validityPeriod.expiryDate", target = "expiryDate")
    ScheduleDTO toDTO(Schedule schedule);
}
