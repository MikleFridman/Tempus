package il.fridman.tempus.timekeeping.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Holiday {
    NEW_YEAR("New Year"),
    INDEPENDENCE_DAY("Independence Day"),
    CHRISTMAS("Christmas");

    private final String description;
}
