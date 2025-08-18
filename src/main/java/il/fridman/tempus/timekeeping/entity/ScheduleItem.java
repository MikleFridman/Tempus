package il.fridman.tempus.timekeeping.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.listener.ScheduleItemListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(ScheduleItemListener.class)
@SQLRestriction("is_deleted = false")
@Table(name = "schedule_items")
public class ScheduleItem extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    @NotNull(message = "Schedule cannot be null")
    private Schedule schedule;

    @NotNull(message = "Day of week cannot be null")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Shift cannot be null")
    @Column(name = "shift", nullable = false)
    private int shift;

    @NotNull(message = "Start time cannot be null")
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    public ScheduleItem(Schedule schedule, DayOfWeek dayOfWeek, int shift, LocalTime startTime, LocalTime endTime) {
        this.schedule = schedule;
        this.dayOfWeek = dayOfWeek;
        this.shift = shift;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
