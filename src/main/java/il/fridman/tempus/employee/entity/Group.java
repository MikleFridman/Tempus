package il.fridman.tempus.employee.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.salary.entity.Payroll;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "groups")
public class Group extends BasicEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
        name = "groups_employees",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "groups_earningrs",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "earning_id")
    )
    private Set<Payroll> earnings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "groups_deductions",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "deduction_id")
    )
    private Set<Payroll> deductions = new HashSet<>();

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
