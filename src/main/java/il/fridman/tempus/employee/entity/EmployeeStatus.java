package il.fridman.tempus.employee.entity;

import il.fridman.tempus.general.dto.Dto;
import il.fridman.tempus.employee.dto.EmployeeStatusDTO;
import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.employee.enums.StatusForEmployee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "employee_statuses")
@Dto(target = EmployeeStatusDTO.class)
public class EmployeeStatus extends TimeVaryingEntity {

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private StatusForEmployee status;

    @Column(name = "number")
    private int number;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public EmployeeStatus(StatusForEmployee status, LocalDate startDate, Employee employee) {
        this.status = status;
        this.setStartDate(startDate);
        this.setExpiryDate(null);
        this.employee = employee;
    }
}
