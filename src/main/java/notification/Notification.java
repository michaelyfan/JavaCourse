package notification;

public abstract class Notification {
    private String msg;

    public Notification(String s) {
        this.msg = s;
    }

    public String message() {
        return this.msg;
    }

    public abstract String channel();

    public String summary() {
        return String.format("[%s] %s", this.channel(), this.msg);
    }
}
