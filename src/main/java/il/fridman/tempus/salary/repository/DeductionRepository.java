package il.fridman.tempus.salary.repository;

import il.fridman.tempus.salary.entity.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionRepository extends JpaRepository<Deduction, Long> {
}
