package il.fridman.tempus.timekeeping.repository;

import il.fridman.tempus.timekeeping.entity.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {

    @Query("SELECT si FROM ScheduleItem si WHERE si.schedule.id = :id")
    List<ScheduleItem> getAllByScheduleId(Long id);
}
