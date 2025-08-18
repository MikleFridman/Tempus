package il.fridman.tempus.salary.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.salary.entity.Earning;
import il.fridman.tempus.salary.repository.EarningRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class EarningService implements EntityService<Earning> {

    private final EarningRepository earningRepository;

    @Override
    @Transactional
    public Earning save(Earning entity) {
        return earningRepository.save(entity);
    }

    @Override
    @Transactional
    public Earning update(Earning entity) {
        return earningRepository.save(entity);
    }

    @Override
    public Earning getById(Long id) {
        return earningRepository.findById(id).orElse(null);
    }

    @Override
    public List<Earning> getAll() {
        return earningRepository.findAll();
    }

    public List<Earning> getByEmployee(Employee employee, LocalDate date) {
        List<Earning> earnings = earningRepository.findAllByEmployee(employee);
        return earnings.stream().filter(TimeVaryingEntity::isValid).toList();
    }
}
