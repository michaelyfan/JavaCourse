package banking.exceptions;

public class InvalidMoneyException extends IllegalArgumentException {
    public InvalidMoneyException(String msg) {
        super(msg);
    }
}