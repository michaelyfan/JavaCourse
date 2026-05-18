package notification;

public interface Retryable {
    public int maxAttempts();
    public default boolean shouldRetry(int attemptsSoFar) {
        return attemptsSoFar < this.maxAttempts();
    }
}
