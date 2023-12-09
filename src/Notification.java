abstract public class Notification {
    protected String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public abstract void saveNotification();

    public abstract void deleteNotification();
}
