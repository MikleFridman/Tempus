package il.fridman.tempus.timekeeping.controller;

import il.fridman.tempus.timekeeping.dto.ScheduleDTO;
import il.fridman.tempus.timekeeping.entity.Schedule;
import il.fridman.tempus.timekeeping.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/get")
    public ResponseEntity<ScheduleDTO> getScheduleById(@RequestParam Long id) {
        Schedule schedule = scheduleService.getById(id);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(scheduleService.getDTO(schedule));
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Schedule>> getAllSchedules() {
        Iterable<Schedule> schedules = scheduleService.getAll();
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/new")
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.save(schedule);
        return ResponseEntity.ok(savedSchedule);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSchedule(@RequestParam Long id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
