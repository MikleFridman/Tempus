package il.fridman.tempus.employee.dto;

import il.fridman.tempus.employee.enums.StatusForEmployee;
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
public class EmployeeStatusDTO {

    private Long id;

    @NotNull(message = "Status cannot be null")
    private StatusForEmployee status;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate expiryDate;
}
