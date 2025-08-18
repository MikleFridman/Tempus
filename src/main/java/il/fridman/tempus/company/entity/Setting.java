package il.fridman.tempus.company.entity;

import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.entity.Group;
import il.fridman.tempus.general.entity.TimeVaryingEntity;
import il.fridman.tempus.timekeeping.enums.DayType;
import il.fridman.tempus.company.enums.ParameterTarget;
import il.fridman.tempus.company.enums.SettingParameter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "settings")
public class Setting extends TimeVaryingEntity {

    @Column(name = "parameter", nullable = false)
    @Enumerated(EnumType.STRING)
    private SettingParameter parameter;

    @Column(name = "target", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParameterTarget target;

    @OneToMany(mappedBy = "setting", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SettingItem> items = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "settings_groups",
            joinColumns = @JoinColumn(name = "setting_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "settings_employees",
            joinColumns = @JoinColumn(name = "setting_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    public Setting(SettingParameter parameter, ParameterTarget target, LocalDate startDate, LocalDate expiryDate) {
        this.parameter = parameter;
        this.target = target;
        this.setStartDate(startDate);
        this.setExpiryDate(expiryDate);
    }

    public double getValue(DayType dayType, DayOfWeek dayOfWeek) {
        return items.stream()
                .filter(item -> item.getDayType() == dayType && item.getDayOfWeek() == dayOfWeek)
                .mapToDouble(SettingItem::getValue)
                .findFirst()
                .orElse(0.0);
    }

    public int getIntValue(DayType dayType, DayOfWeek dayOfWeek) {
        return items.stream()
                .filter(item -> item.getDayType() == dayType && item.getDayOfWeek() == dayOfWeek)
                .mapToInt(SettingItem::getIntValue)
                .findFirst()
                .orElse(0);
    }

    public String getStringValue(DayType dayType, DayOfWeek dayOfWeek) {
        return items.stream()
                .filter(item -> item.getDayType() == dayType && item.getDayOfWeek() == dayOfWeek)
                .map(SettingItem::getStringValue)
                .findFirst()
                .orElse(null);
    }

    public Boolean getBooleanValue(DayType dayType, DayOfWeek dayOfWeek) {
        return items.stream()
                .filter(item -> item.getDayType() == dayType && item.getDayOfWeek() == dayOfWeek)
                .map(SettingItem::getBooleanValue)
                .findFirst()
                .orElse(null);
    }
}
