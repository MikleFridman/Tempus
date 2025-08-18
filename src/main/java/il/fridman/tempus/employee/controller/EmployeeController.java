package il.fridman.tempus.employee.controller;

import il.fridman.tempus.employee.dto.EmployeeDTO;
import il.fridman.tempus.employee.dto.EmployeeStatusDTO;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.entity.EmployeeStatus;
import il.fridman.tempus.employee.service.EmployeeService;
import il.fridman.tempus.employee.service.EmployeeStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeStatusService employeeStatusService;

    @GetMapping("all")
    public List<EmployeeDTO> getAllEmployees() {
        return  employeeService.getAll().stream()
                .map(employeeService::getDTO).toList();
    }

    @GetMapping("get")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@RequestParam Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeService.getDTO(employee));
    }

    @GetMapping("get_status")
    public ResponseEntity<EmployeeStatusDTO> getEmployeeStatusById(@RequestParam Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        EmployeeStatus status = employee.getCurrentStatus();
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeStatusService.getDTO(status));
    }

    @PostMapping("set_status")
    public ResponseEntity<EmployeeStatusDTO> setEmployeeStatus(@RequestParam Long id,
                                                               @Valid
                                                               @RequestBody EmployeeStatusDTO status) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        EmployeeStatus setStatus = employeeService.setStatus(employee, status.getStatus(), status.getStartDate());
        return ResponseEntity.ok(employeeStatusService.getDTO(setStatus));
    }

    @PostMapping("set_number")
    public ResponseEntity<Integer> setEmployeeNumber(@RequestParam Long id,
                                                     @RequestParam int number,
                                                     @RequestParam LocalDate date) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        int setNumber = employeeService.setNumber(employee, number, date);
        return ResponseEntity.ok(setNumber);
    }

    @PostMapping("new")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        if (savedEmployee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeService.getDTO(savedEmployee));
    }
}
