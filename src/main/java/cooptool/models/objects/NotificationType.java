package cooptool.models.objects;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * NotificationType enum
 */
public enum NotificationType {

    MENTORING_DEMAND(1),
    QUICK_HELP_POST(2);

    private final int value;

    NotificationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Get notification type by its value
     * @param type Value of the notification type we want
     * @return
     */
    public static NotificationType getType(int type) {
        return type <= NotificationType.values().length ? NotificationType.values()[type-1] : null;
    }

    /**
     * Get the notification type in String format
     * @return Notification type in String format
     */
    public String getString() {
        return Arrays.stream(this.toString().split("_"))
                .map(String::toLowerCase)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    /**
     * Get all notification types sorted by value
     * @return Notification types array sorted
     */
    public static NotificationType[] sortedValues() {
        return Arrays.stream(values()).sorted().toArray(NotificationType[]::new);
    }
}
