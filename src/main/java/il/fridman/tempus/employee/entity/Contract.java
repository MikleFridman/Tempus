package il.fridman.tempus.employee.entity;

import il.fridman.tempus.employee.dto.ContractDTO;
import il.fridman.tempus.general.dto.Dto;
import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.company.entity.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "contracts")
@Dto(target = ContractDTO.class)
public class Contract extends TimeVaryingEntity {

    @NotBlank(message = "Contract number cannot be blank")
    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    public Contract(String number, LocalDate startDate, Employee employee, Position position) {
        this.number = number;
        this.setStartDate(startDate);
        this.setExpiryDate(null);
        this.employee = employee;
        this.position = position;
    }
}
