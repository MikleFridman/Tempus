package il.fridman.tempus.timekeeping.controller;

import il.fridman.tempus.timekeeping.dto.TimesheetDTO;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import il.fridman.tempus.timekeeping.mapper.TimesheetMapper;
import il.fridman.tempus.timekeeping.service.TimesheetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timesheet")
@RequiredArgsConstructor
public class TimesheetController {

    private final TimesheetService timesheetService;
    private final TimesheetMapper timesheetMapper;

    @GetMapping("get")
    public List<TimesheetDTO> getTimesheetByEmployeeAndDateRange(@RequestParam Employee employee,
                                                              @RequestParam LocalDate startDate,
                                                              @RequestParam LocalDate endDate) {
        List<Timesheet> timesheets = timesheetService.getByEmployeeAndDateRange(employee, startDate, endDate);
        return timesheetMapper.toDTOList(timesheets);
    }

    @PostMapping("new")
    public ResponseEntity<TimesheetDTO> saveTimesheet(@Valid @RequestBody Timesheet timesheet) {
        Timesheet savedTimesheet = timesheetService.save(timesheet);
        if (savedTimesheet == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(timesheetMapper.toDTO(savedTimesheet));
    }

    @PutMapping("update")
    public TimesheetDTO updateTimesheet(@Valid @RequestBody Timesheet timesheet) {
        Timesheet updatedTimesheet = timesheetService.update((timesheet));
        return timesheetMapper.toDTO(updatedTimesheet);
    }
}
