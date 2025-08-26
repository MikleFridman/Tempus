package il.fridman.tempus.salary.repository;

import il.fridman.tempus.salary.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
