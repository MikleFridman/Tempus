package il.fridman.tempus.timekeeping.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.timekeeping.dto.ScheduleDTO;
import il.fridman.tempus.timekeeping.entity.Schedule;
import il.fridman.tempus.timekeeping.entity.ScheduleItem;
import il.fridman.tempus.timekeeping.mapper.ScheduleMapper;
import il.fridman.tempus.timekeeping.repository.ScheduleItemRepository;
import il.fridman.tempus.timekeeping.repository.ScheduleRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Loggable
@RequiredArgsConstructor
public class ScheduleService implements EntityService<Schedule> {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleItemRepository scheduleItemRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public Schedule save(Schedule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Schedule entity cannot be null.");
        }
        Schedule schedule = scheduleRepository.save(entity);
        List<ScheduleItem> items = new ArrayList<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            ScheduleItem scheduleItem = new ScheduleItem();
            scheduleItem.setSchedule(entity);
            scheduleItem.setDayOfWeek(day);
            scheduleItem.setShift(1);
            scheduleItem.setStartTime(LocalTime.of(8, 0));
            scheduleItem.setEndTime(LocalTime.of(17, 0));
            scheduleItemRepository.save(scheduleItem);
            items.add(scheduleItem);
        }
        schedule.setItems(items);
        return schedule;
    }

    @Override
    @Transactional
    public Schedule update(Schedule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Schedule entity cannot be null.");
        }
        return scheduleRepository.save(entity);
    }

    @Override
    public Schedule getById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    //DTO methods
    public ScheduleDTO getDTO(Schedule entity) {
        return entity != null ? scheduleMapper.toDTO(entity) : new ScheduleDTO();
    }
}
