package il.fridman.tempus.timekeeping.dto;

import il.fridman.tempus.company.entity.Location;
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
public class ScheduleDTO {

    private Long id;

    @NotNull(message = "Location cannot be null")
    private Location location;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private LocalDate expiryDate;
}
