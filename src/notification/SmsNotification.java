package notification;

public class SmsNotification extends Notification implements Retryable {
    public SmsNotification(String msg) {
        super(msg);
    }

    @Override
    public String channel() {
        return "SMS";
    }

    @Override
    public int maxAttempts() {
        return 3;
    }
}