package il.fridman.tempus.timekeeping.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.timekeeping.enums.Holiday;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "calendar")
public class Calendar extends BasicEntity {

    @Column(name = "year", nullable = false)
    @NotNull(message = "Year cannot be null")
    private int year;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Holiday cannot be null")
    @Column(name = "holiday", nullable = false)
    private Holiday holiday;

    @Column(name = "description")
    private String description;
}
