package il.fridman.tempus.timekeeping.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.timekeeping.entity.Timesheet;
import il.fridman.tempus.timekeeping.enums.Timecode;
import il.fridman.tempus.timekeeping.repository.TimesheetRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class TimesheetService implements EntityService<Timesheet> {

    private final TimesheetRepository timesheetRepository;

    @Override
    @Transactional
    public Timesheet save(Timesheet entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Timesheet entity cannot be null.");
        }
        return timesheetRepository.save(entity);
    }

    @Override
    @Transactional
    public Timesheet update(Timesheet entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Timesheet entity cannot be null.");
        }
        return timesheetRepository.save(entity);
    }

    @Override
    public Timesheet getById(Long id) {
        return timesheetRepository.findById(id).orElseThrow(null);
    }

    @Override
    public List<Timesheet> getAll() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> getByEmployeeAndDateRange(Employee employee, LocalDate startDate, LocalDate endDate) {
        return timesheetRepository.findByEmployeeAndDateRange(employee, startDate, endDate);
    }

    public List<Timesheet> getByDate(Employee employee, LocalDate date) {
        return timesheetRepository.findByEmployeeAndDate(employee, date);
    }

    public List<Timesheet> getByMonthAndYear(Employee employee, int month, int year) {
        return timesheetRepository.findByEmployeeAndMonthAndYear(employee, month, year);
    }

    public List<Timesheet> getByTimecodeAndPeriod(Employee employee, Timecode timecode, int month, int year) {
        return timesheetRepository.findByEmployeeAndTimecodeAndMonthAndYear(employee, timecode, month, year);
    }
}
