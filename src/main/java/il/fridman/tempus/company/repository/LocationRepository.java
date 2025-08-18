package il.fridman.tempus.company.repository;

import il.fridman.tempus.company.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
