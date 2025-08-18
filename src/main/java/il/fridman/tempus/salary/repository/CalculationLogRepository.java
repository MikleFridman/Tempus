package il.fridman.tempus.salary.repository;

import il.fridman.tempus.salary.entity.CalculationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationLogRepository extends JpaRepository<CalculationLog, Long> {
}
