package il.fridman.tempus.salary.controller;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.service.EmployeeService;
import il.fridman.tempus.salary.dto.CalculationDTO;
import il.fridman.tempus.salary.entity.Calculation;
import il.fridman.tempus.salary.entity.Earning;
import il.fridman.tempus.salary.mapper.CalculationMapper;
import il.fridman.tempus.salary.service.CalculationService;
import il.fridman.tempus.salary.service.DeductionCalculationService;
import il.fridman.tempus.salary.service.EarningCalculationService;
import il.fridman.tempus.salary.service.EarningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final EarningCalculationService earningCalculationService;
    private final DeductionCalculationService deductionCalculationService;
    private final EmployeeService employeeService;
    private final EarningService earningService;
    private final CalculationService calculationService;
    private final CalculationMapper calculationMapper;

    @GetMapping("/calculate")
    public ResponseEntity<List<CalculationDTO>> calculateSalary(@Valid @RequestParam Long employeeId,
                                                          @Valid @RequestParam YearMonth payPeriod) {
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        calculationService.clear(employee, payPeriod);
        List<CalculationDTO> calculations = new ArrayList<>();
        List<Earning> earnings = earningService.getByEmployee(employee, payPeriod.atDay(1));
        for (Earning earning : earnings) {
            Calculation calculation = earningCalculationService.calculate(employee, earning, payPeriod);
            if (calculation != null) {
                calculations.add(calculationMapper.toDTO(calculation));
            }
        }
        return ResponseEntity.ok(calculations);
    }
}
