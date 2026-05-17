package banking.exceptions;

public class OverdrawException extends IllegalStateException {
    public OverdrawException(String msg) {
        super(msg);
    }
}