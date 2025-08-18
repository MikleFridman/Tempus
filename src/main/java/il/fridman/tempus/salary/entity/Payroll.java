package il.fridman.tempus.salary.entity;

import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.salary.enums.PayrollBasis;
import il.fridman.tempus.salary.enums.PayrollFrequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@SQLRestriction("is_deleted = false")
@Table(name = "payrolls")
public abstract class Payroll extends TimeVaryingEntity {

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "basis")
    @Enumerated(EnumType.STRING)
    private PayrollBasis basis;

    @Column(name = "rate", nullable = false)
    @NotNull(message = "Rate cannot be null")
    private double rate;

    @Column(name = "frequency", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Frequency cannot be null")
    private PayrollFrequency frequency;

    @Column(name = "reverse_calculation")
    private Boolean reverseCalculation = false;
}
