package il.fridman.tempus.salary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDTO {

    private Long id;

    @NotNull(message = "Payroll ID cannot be null")
    private Long payrollId;

    @NotBlank(message = "Payroll name cannot be blank")
    private String payrollName;

    @NotNull(message = "Employee ID cannot be null")
    private Long employeeId;

    @NotBlank(message = "Employee name cannot be blank")
    private String employeeName;

    @NotNull(message = "Pay period cannot be null")
    private YearMonth payPeriod;

    @NotNull(message = "Amount cannot be null")
    private double amount;

    @NotNull(message = "Tariff cannot be null")
    private double tariff;

    @NotNull(message = "Sum cannot be null")
    private double sum;
}
