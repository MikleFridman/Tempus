package il.fridman.tempus.general.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class TimeVaryingEntity extends BasicEntity{

    @Embedded
    @AttributeOverride(name = "startDate", column = @Column(name = "start_date", nullable = false))
    @AttributeOverride(name = "expiryDate", column = @Column(name = "expiry_date"))
    private ValidityPeriod validityPeriod;

    public boolean isValid() {
        if (validityPeriod != null) {
            return validityPeriod.isValid();
        }
        return false;
    }

    public boolean isValidOnDate(LocalDate date) {
        if (validityPeriod != null) {
            return validityPeriod.isValidOnDate(date);
        }
        return false;
    }

    public LocalDate getStartDate() {
        if (validityPeriod != null) {
            return validityPeriod.getStartDate();
        }
        return null;
    }

    public LocalDate getExpiryDate() {
        if (validityPeriod != null) {
            return validityPeriod.getExpiryDate();
        }
        return null;
    }

    public void setStartDate(LocalDate date) {
        if (validityPeriod != null) {
            validityPeriod.setStartDate(date);
        }
    }

    public void setExpiryDate(LocalDate date) {
        if (validityPeriod != null) {
            validityPeriod.setExpiryDate(date);
        }
    }
}
