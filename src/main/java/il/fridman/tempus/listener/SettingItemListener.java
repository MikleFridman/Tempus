package il.fridman.tempus.listener;

import il.fridman.tempus.company.entity.Setting;
import il.fridman.tempus.company.entity.SettingItem;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class SettingItemListener {

    @PrePersist
    @PreRemove
    @PreUpdate
    public void onChange(SettingItem item) {
        Setting setting = item.getSetting();
        if (setting != null) {
            setting.setUpdatedAt(LocalDateTime.now());
        }
    }
}
