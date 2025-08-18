package il.fridman.tempus.employee.service;

import il.fridman.tempus.employee.dto.EmployeeDTO;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.entity.EmployeeStatus;
import il.fridman.tempus.employee.enums.StatusForEmployee;
import il.fridman.tempus.employee.mapper.EmployeeMapper;
import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.repository.EmployeeRepository;
import il.fridman.tempus.employee.repository.EmployeeStatusRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class EmployeeService implements EntityService<Employee> {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EmployeeStatusRepository employeeStatusRepository;

    @Override
    @Transactional
    public Employee save(Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity cannot be null.");
        }
        Employee employee = employeeRepository.save(entity);
        EmployeeStatus employeeStatus = setStatus(employee, StatusForEmployee.INACTIVE, LocalDate.now());
        employee.setCurrentStatus(employeeStatus);
        return employee;
    }

    @Override
    @Transactional
    public Employee update(Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employee entity cannot be null.");
        }
        return employeeRepository.save(entity);
    }

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getByNumber(int number) {
        return employeeStatusRepository.findEmployeeByNumber(number).orElse(null);
    }

    public Employee getByEmail(String email) {
        return null;
    }

    public List<Employee> getAllByStatus(StatusForEmployee status) {
        return employeeRepository.getAllByStatus(status);
    }

    @Transactional
    public EmployeeStatus setStatus(Employee employee, StatusForEmployee status, LocalDate date) {
        EmployeeStatus statusByDate = employeeStatusRepository.findByDate(employee, date).orElse(null);
        if (statusByDate != null) {
            if (statusByDate.getStatus() == status) {
                return statusByDate;
            }
            statusByDate.setExpiryDate(date.minusDays(1));
            employeeStatusRepository.save(statusByDate);
        }
        EmployeeStatus newStatus = new EmployeeStatus(status, date, employee);
        updateEmployeeFromStatus(employee, newStatus);
        return employeeStatusRepository.save(newStatus);
    }

    @Transactional
    public int setNumber(Employee employee, int number, LocalDate date) {
        EmployeeStatus status = employeeStatusRepository.findByDate(employee, date).orElse(null);
        if (status == null) {
            status = setStatus(employee, StatusForEmployee.INACTIVE, date);
        }
        status.setNumber(number);
        updateEmployeeFromStatus(employee, status);
        return employeeStatusRepository.save(status).getNumber();
    }

    public void updateEmployeeFromStatus(Employee employee, EmployeeStatus status) {
        employee.setNumber(status.getNumber());
        employee.setCurrentStatus(status);
    }

    //DTO methods
    public EmployeeDTO getDTO(Employee entity) {
        return entity != null ? employeeMapper.toDTO(entity) : new EmployeeDTO();
    }
}
