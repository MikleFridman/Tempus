package il.fridman.tempus.company.mapper;

import il.fridman.tempus.company.dto.SettingDTO;
import il.fridman.tempus.company.entity.Setting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingMapper {

    @Mapping(source = "validityPeriod.startDate", target = "startDate")
    @Mapping(source = "validityPeriod.expiryDate", target = "expiryDate")
    SettingDTO toDTO(Setting setting);
}
