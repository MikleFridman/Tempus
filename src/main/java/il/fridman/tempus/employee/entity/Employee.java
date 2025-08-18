package il.fridman.tempus.employee.entity;

import il.fridman.tempus.general.dto.Dto;
import il.fridman.tempus.employee.dto.EmployeeDTO;
import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.timekeeping.entity.Schedule;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "employees")
@Dto(target = EmployeeDTO.class)
public class Employee extends BasicEntity {

    @NotBlank(message = "First name cannot be blank")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    List<EmployeeStatus> statuses;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timesheet> timesheets;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    private List<Group> groups;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    @Transient
    private EmployeeStatus currentStatus;

    @Transient
    private int number;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Employee(String firstName, String lastName, String email, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    @PostLoad
    public void updateCurrentStatusAndNumber() {
        EmployeeStatus status = statuses.stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(EmployeeStatus::getStatus).reversed()))
                .filter(Objects::nonNull)
                .filter(EmployeeStatus::isValid).findFirst().orElse(null);
        if (status != null) {
            this.currentStatus = status;
            this.number = status.getNumber();
        }
    }
}
