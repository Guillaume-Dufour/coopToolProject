package cooptool.models.objects;

public enum NotificationType {

    MENTORING_DEMAND, QUICK_HELP_POST, MESSAGE;

    public static NotificationType getType(int type) {
        switch (type) {
            case 1:
                return MENTORING_DEMAND;
            case 2:
                return QUICK_HELP_POST;
            case 3:
                return MESSAGE;
            default:
                return null;
        }
    }
}
