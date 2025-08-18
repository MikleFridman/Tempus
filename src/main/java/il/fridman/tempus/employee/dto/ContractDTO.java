package il.fridman.tempus.employee.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ContractDTO {

    private Long id;

    @NotBlank(message = "Contract number cannot be blank")
    private String number;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate expiryDate;
}
