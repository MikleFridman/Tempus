package il.fridman.tempus.company.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.timekeeping.enums.DayType;
import il.fridman.tempus.listener.SettingItemListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(SettingItemListener.class)
@SQLRestriction("is_deleted = false")
@Table(name = "setting_items")
public class SettingItem extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "setting_id", nullable = false)
    @NotNull(message = "Setting cannot be null")
    private Setting setting;

    @Column(name = "day_type")
    @Enumerated(EnumType.STRING)
    private DayType dayType;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "value")
    private double value;

    @Column(name = "integer_value")
    private int intValue;

    @Column(name = "string_value", length = 100)
    private String stringValue;

    @Column(name = "boolean_value")
    private Boolean booleanValue;
}
