package il.fridman.tempus.company.repository;

import il.fridman.tempus.company.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
