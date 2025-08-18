package il.fridman.tempus.company.controller;

import il.fridman.tempus.company.dto.SettingDTO;
import il.fridman.tempus.company.entity.Setting;
import il.fridman.tempus.company.entity.SettingItem;
import il.fridman.tempus.company.enums.SettingParameter;
import il.fridman.tempus.company.mapper.SettingMapper;
import il.fridman.tempus.company.service.SettingService;
import il.fridman.tempus.employee.entity.Employee;
import il.fridman.tempus.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;
    private final EmployeeService employeeService;
    private final SettingMapper settingMapper;

    @GetMapping("/get")
    public ResponseEntity<SettingDTO> getSetting(@RequestParam Long id) {
        Setting setting = settingService.getById(id);
        if (setting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(settingMapper.toDTO(setting));
    }

    @GetMapping("get-valid-setting-for-employee")
    public ResponseEntity<SettingDTO> getSettingByEmployee(@RequestParam Long employeeId,
                                                           @RequestParam SettingParameter parameter,
                                                           @RequestParam LocalDate date) {
        Employee employee = employeeService.getById(employeeId);
        Setting setting = settingService.getValidSetting(employee, parameter, date);
        if (setting == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(settingMapper.toDTO(setting));
    }

    @PostMapping("/new")
    public ResponseEntity<SettingDTO> createSetting(@Valid @RequestBody Setting setting) {
        for (SettingItem item : setting.getItems()) {
            item.setSetting(setting);
        }
        Setting savedSetting = settingService.save(setting);
        if (savedSetting == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(settingMapper.toDTO(savedSetting));
    }

    @PostMapping("/update")
    public ResponseEntity<SettingDTO> updateSetting(@Valid @RequestBody Setting setting) {
        for (SettingItem item : setting.getItems()) {
            item.setSetting(setting);
        }
        Setting updatedSetting = settingService.update(setting);
        if (updatedSetting == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(settingMapper.toDTO(updatedSetting));
    }
}
