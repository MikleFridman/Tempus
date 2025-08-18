package il.fridman.tempus.company.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.company.entity.Department;
import il.fridman.tempus.company.repository.DepartmentRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class DepartmentService implements EntityService<Department> {

    private final DepartmentRepository departmentRepository;
    private final CompanyService companyService;

    @Override
    @Transactional
    public Department save(Department entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity cannot be null.");
        }
        if (entity.getCompany().getId() != null) {
            companyService.getById(entity.getCompany().getId());
        }
        else {
            throw new IllegalArgumentException("Company must be set for the department.");
        }
        return departmentRepository.save(entity);
    }

    @Override
    @Transactional
    public Department update(Department entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Department entity cannot be null.");
        }
        return departmentRepository.save(entity);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }
}
