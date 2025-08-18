package il.fridman.tempus.employee.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.dto.EmployeeStatusDTO;
import il.fridman.tempus.employee.entity.EmployeeStatus;
import il.fridman.tempus.employee.mapper.EmployeeStatusMapper;
import il.fridman.tempus.employee.repository.EmployeeStatusRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class EmployeeStatusService implements EntityService<EmployeeStatus> {

    private final EmployeeStatusRepository employeeStatusRepository;
    private final EmployeeStatusMapper employeeStatusMapper;

    @Override
    @Transactional
    public EmployeeStatus save(EmployeeStatus entity) {
        if (entity == null) {
            throw new IllegalArgumentException("EmployeeStatus cannot be null.");
        }
        return employeeStatusRepository.save(entity);
    }

    @Override
    @Transactional
    public EmployeeStatus update(EmployeeStatus entity) {
        if (entity == null) {
            throw new IllegalArgumentException("EmployeeStatus cannot be null.");
        }
        return employeeStatusRepository.save(entity);
    }

    @Override
    public EmployeeStatus getById(Long id) {
        return employeeStatusRepository.findById(id).orElse(null);
    }

    @Override
    public List<EmployeeStatus> getAll() {
        return List.of();
    }

    //DTO methods
    public EmployeeStatusDTO getDTO(EmployeeStatus entity) {
        return entity != null ? employeeStatusMapper.toDTO(entity) : new EmployeeStatusDTO();
    }
}
