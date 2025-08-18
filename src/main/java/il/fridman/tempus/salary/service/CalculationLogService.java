package il.fridman.tempus.salary.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.general.service.EntityService;
import il.fridman.tempus.salary.entity.CalculationLog;
import il.fridman.tempus.salary.repository.CalculationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class CalculationLogService implements EntityService<CalculationLog> {

    private final CalculationLogRepository calculationLogRepository;

    @Override
    public CalculationLog save(CalculationLog entity) {
        return calculationLogRepository.save(entity);
    }

    @Override
    public CalculationLog update(CalculationLog entity) {
        return calculationLogRepository.save(entity);
    }

    @Override
    public CalculationLog getById(Long id) {
        return calculationLogRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<CalculationLog> getAll() {
        return calculationLogRepository.findAll();
    }
}
