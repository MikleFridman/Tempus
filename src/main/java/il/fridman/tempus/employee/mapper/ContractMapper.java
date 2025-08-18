package il.fridman.tempus.employee.mapper;

import il.fridman.tempus.employee.dto.ContractDTO;
import il.fridman.tempus.employee.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper {

     @Mapping(source = "validityPeriod.startDate", target = "startDate")
     @Mapping(source = "validityPeriod.expiryDate", target = "expiryDate")
     ContractDTO toDTO(Contract contract);
}