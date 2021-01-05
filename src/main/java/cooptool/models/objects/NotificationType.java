package cooptool.models.objects;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum NotificationType {

    MENTORING_DEMAND(1),
    QUICK_HELP_POST(2),
    MESSAGE(3);

    private final int value;

    NotificationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NotificationType getType(int type) {
        return type < NotificationType.values().length ? NotificationType.values()[type-1] : null;
    }

    public String getString() {
        return Arrays.stream(this.toString().split("_"))
                .map(String::toLowerCase)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static List<String> getStringValues() {
        return Arrays.stream(NotificationType.values())
                .map(NotificationType::getString)
                .collect(Collectors.toList());
    }
}
