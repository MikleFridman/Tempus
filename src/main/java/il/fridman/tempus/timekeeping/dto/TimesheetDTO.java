package il.fridman.tempus.timekeeping.dto;

import il.fridman.tempus.timekeeping.enums.Timecode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetDTO {

    private Long id;

    @NotNull(message = "Employee ID cannot be null")
    private Long employeeId;

    @NotNull(message = "Year cannot be null")
    private int year;

    @NotNull(message = "Month cannot be null")
    private int month;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Timecode cannot be null")
    private Timecode timecode;

    private LocalTime entryTime;

    private LocalTime exitTime;

    private Boolean approved;
}
