package il.fridman.tempus.company.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "companies")
public class Company extends BasicEntity {

    @NotBlank(message = "Company name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;
}
