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
@Table(name = "algorithm_items")
public class AlgorithmItem extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "algorithm_id", nullable = false)
    @NotNull(message = "Algorithm cannot be null")
    private Algorithm algorithm;

    @Column(name = "order", nullable = false)
    @NotNull(message = "Order cannot be null")
    private int order;

    @Column(name = "description")
    private String description;
}
