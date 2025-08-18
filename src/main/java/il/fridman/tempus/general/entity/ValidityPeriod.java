package il.fridman.tempus.general.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ValidityPeriod {

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate expiryDate;

    public boolean isValid() {
        return isValidOnDate(LocalDate.now());
    }

    public boolean isValidOnDate(LocalDate date) {
        if (date.isBefore(startDate)) {
            return false;
        }
        if (expiryDate == null) {
            return true;
        } else {
            return date.isBefore(expiryDate) || date.isEqual(expiryDate);
        }
    }
}
