package il.fridman.tempus.company.service;

import il.fridman.tempus.aspect.Loggable;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.company.entity.Setting;
import il.fridman.tempus.company.entity.SettingItem;
import il.fridman.tempus.company.enums.SettingParameter;
import il.fridman.tempus.company.repository.SettingRepository;
import il.fridman.tempus.general.service.EntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Loggable
@RequiredArgsConstructor
public class SettingService implements EntityService<Setting> {

    private final SettingRepository settingRepository;

    @Override
    @Transactional
    public Setting save(Setting entity) {
        return settingRepository.save(entity);
    }

    @Override
    @Transactional
    public Setting update(Setting entity) {
        Setting setting = getById(entity.getId());
        if (setting == null) {
            throw new IllegalArgumentException("Setting with id " + entity.getId() + " does not exist.");
        }
        setting.setParameter(entity.getParameter());
        setting.setTarget(entity.getTarget());
        setting.setStartDate(entity.getStartDate());
        setting.setExpiryDate(entity.getExpiryDate());
        setting.getItems().clear();
        for (SettingItem item : entity.getItems()) {
            item.setSetting(setting);
            setting.getItems().add(item);
        }
        setting.getGroups().clear();
        setting.getGroups().addAll(entity.getGroups());
        setting.getEmployees().clear();
        setting.getEmployees().addAll(entity.getEmployees());

        return setting;
    }

    @Override
    public Setting getById(Long id) {
        return settingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    public Setting getValidSetting(Employee employee, SettingParameter parameter, LocalDate date) {
        List<Setting> settings = settingRepository.findByParameterAndEmployee(parameter, employee);
        return settings.stream()
                .filter(setting -> setting.isValidOnDate(date))
                .findFirst()
                .orElse(null);
    }

    public Map<String, Double> getParameterValues(Employee employee, SettingParameter parameter, LocalDate date) {
        Map<String, Double> result = new java.util.HashMap<>();
        Setting setting = getValidSetting(employee, parameter, date);
        if (setting != null) {
            for (SettingItem item : setting.getItems()) {
                String key;
                if (item.getDayOfWeek() == null) {
                    key = item.getDayType().name();
                } else if (item.getDayType() == null) {
                    key = item.getDayOfWeek().toString();
                } else {
                    key = item.getDayOfWeek() + "_" + item.getDayType();
                }
                result.put(key, item.getValue());
            }
        }
        return result;
    }
}
