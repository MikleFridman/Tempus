package il.fridman.tempus.company.entity;

import il.fridman.tempus.general.entity.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "locations")
public class Location  extends BasicEntity {

    @NotBlank(message = "Location name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 400, message = "Address cannot exceed 400 characters")
    @Column(name = "address", nullable = false, length = 400)
    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Location(String name, String address, Company company) {
        this.name = name;
        this.address = address;
        this.company = company;
    }
}
