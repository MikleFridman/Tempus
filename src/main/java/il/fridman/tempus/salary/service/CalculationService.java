package il.fridman.tempus.salary.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.redis.RedisService;
import il.fridman.tempus.salary.entity.Calculation;
import il.fridman.tempus.salary.repository.CalculationRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class CalculationService implements EntityService<Calculation> {

    private final CalculationRepository calculationRepository;
    private final RedisService redisService;

    @Override
    @Transactional
    public Calculation save(Calculation entity) {
        return calculationRepository.save(entity);
    }

    @Override
    @Transactional
    public Calculation update(Calculation entity) {
        return calculationRepository.save(entity);
    }

    @Override
    public Calculation getById(Long id) {
        return calculationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Calculation> getAll() {
        return calculationRepository.findAll();
    }

    @Transactional
    public void clearLog(Calculation calculation) {
        calculation.getLogs().clear();
        calculationRepository.save(calculation);
    }

    @Transactional
    public void clear(Employee employee, YearMonth payPeriod) {
        List<Calculation> calculations = calculationRepository.findByEmployeeAndPayPeriod(employee, payPeriod);
        for (Calculation calculation : calculations) {
            clearLog(calculation);
            calculationRepository.delete(calculation);
        }
        String redisKey = "salary:" + employee.getId() + ":" + payPeriod.toString();
        redisService.deleteHashValue(redisKey);
    }
}
