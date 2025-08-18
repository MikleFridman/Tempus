package il.fridman.tempus.salary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "deduction_templates")
public class DeductionTemplate extends Payroll {

}
