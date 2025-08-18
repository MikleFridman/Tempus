package il.fridman.tempus.timekeeping.entity;

import il.fridman.tempus.general.dto.Dto;
import il.fridman.tempus.timekeeping.dto.ScheduleDTO;
import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.company.entity.Location;
import il.fridman.tempus.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "schedules")
@Dto(target = ScheduleDTO.class)
public class Schedule extends TimeVaryingEntity {

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull(message = "Location cannot be null")
    private Location location;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleItem> items;

    @ManyToMany
    @JoinTable(name = "schedules_employees",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;

    public Schedule(Location location, LocalDate startDate, LocalDate expiryDate) {
        this.location = location;
        this.setStartDate(startDate);
        this.setExpiryDate(expiryDate);
    }
}
