package il.fridman.tempus.listener;

import il.fridman.tempus.timekeeping.entity.Schedule;
import il.fridman.tempus.timekeeping.entity.ScheduleItem;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class ScheduleItemListener {

    @PrePersist
    @PreRemove
    @PreUpdate
    public void onChange(ScheduleItem scheduleItem) {
        Schedule schedule = scheduleItem.getSchedule();
        if (schedule != null) {
            schedule.setUpdatedAt(LocalDateTime.now());
        }
    }
}
