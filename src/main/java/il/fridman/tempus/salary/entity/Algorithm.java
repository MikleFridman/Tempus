package il.fridman.tempus.salary.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import jakarta.persistence.*;
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
@Table(name = "algorithms")
public class Algorithm extends BasicEntity {

    @OneToOne
    @JoinColumn(name = "payroll_id", nullable = false)
    @NotNull(message = "Payroll cannot be null")
    private Payroll payroll;

    @OneToMany(mappedBy = "algorithm", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<AlgorithmItem> items;
}
