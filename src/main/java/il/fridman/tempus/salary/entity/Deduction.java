package il.fridman.tempus.salary.entity;

import il.fridman.tempus.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "deductions")
public class Deduction extends DeductionTemplate {

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "Employee cannot be null")
    private Employee employee;
}
