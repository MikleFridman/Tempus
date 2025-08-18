package il.fridman.tempus.salary.entity;

import il.fridman.tempus.general.entity.BasicEntity;
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
@SQLRestriction("is_deleted = false")
@Table(name = "calculation_log")
public class CalculationLog extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "calculation_id", nullable = false)
    @NotNull(message = "Calculation cannot be null")
    private Calculation calculation;

    @Column(name = "stage", nullable = false, length = 100)
    @NotBlank(message = "Stage cannot be blank")
    private String stage;

    @Column(name = "message", nullable = false)
    @NotBlank(message = "Message cannot be blank")
    private String message;
}
