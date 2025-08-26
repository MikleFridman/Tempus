package il.fridman.tempus.salary.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import il.fridman.tempus.employee.entity.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "calculations")
public class Calculation extends BasicEntity {

    @Column(name = "pay_period", nullable = false, length = 7)
    @NotNull(message = "Pay period cannot be null")
    private YearMonth payPeriod;

    @ManyToOne
    @JoinColumn(name = "payroll_id", nullable = false)
    @NotNull(message = "Payroll cannot be null")
    private Payroll payroll;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @NotNull(message = "Employee cannot be null")
    private Employee employee;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount cannot be null")
    private double amount;

    @Column(name = "tariff", nullable = false)
    @NotNull(message = "Tax cannot be null")
    private double tariff;

    @Column(name = "sum", nullable = false)
    @NotNull(message = "Sum cannot be null")
    private double sum;

    @OneToMany(mappedBy = "calculation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CalculationLog> logs;

    public Calculation(YearMonth payPeriod, Payroll payroll, Employee employee) {
        this.payPeriod = payPeriod;
        this.payroll = payroll;
        this.employee = employee;
        this.tariff = payroll.getRate();
        this.logs = new ArrayList<>();
    }

    public void setLog(String stage, String message) {
        logs.add(new CalculationLog(this, stage, message));
    }
}
