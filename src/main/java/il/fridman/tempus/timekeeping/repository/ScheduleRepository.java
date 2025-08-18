package il.fridman.tempus.timekeeping.repository;

import il.fridman.tempus.timekeeping.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
