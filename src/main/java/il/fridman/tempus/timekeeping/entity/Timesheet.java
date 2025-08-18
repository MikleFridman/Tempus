package il.fridman.tempus.timekeeping.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.company.entity.Location;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.timekeeping.enums.Timecode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "timesheets")
public class Timesheet extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull(message = "Location cannot be null")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "Employee cannot be null")
    private Employee employee;

    @Column(name = "year", nullable = false, length = 4)
    @NotNull(message = "Year cannot be null")
    private int year;

    @Column(name = "month", nullable = false, length = 2)
    @NotNull(message = "Month cannot be null")
    private int month;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @Column(name = "timecode", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Timecode cannot be null")
    private Timecode timecode;

    @Column(name = "entry_time")
    private LocalTime entryTime;

    @Column(name = "exit_time")
    private LocalTime exitTime;

    @Column(name = "approved")
    private Boolean approved = false;

    public double getHours() {
        if (entryTime != null && exitTime != null) {
            return Duration.between(entryTime, exitTime).toMinutes() / 60.0;
        }
        return 0.0;
    }
}
