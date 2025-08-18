package il.fridman.tempus.company.dto;

import il.fridman.tempus.company.enums.ParameterTarget;
import il.fridman.tempus.company.enums.SettingParameter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingDTO {

    private Long id;

    @NotNull(message = "Parameter cannot be null")
    private SettingParameter parameter;

    @NotNull(message = "Target cannot be null")
    private ParameterTarget target;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate expiryDate;

}
