package operation;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum Operation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;
    private static final Map<String, Operation> BY_SYMBOL = new HashMap<>();
    private static final Map<Operation, String> DESCRIPTIONS = new EnumMap<>(Operation.class);
    static {
        for (Operation op : Operation.values()) {
            BY_SYMBOL.put(op.symbol(), op);
        }
        DESCRIPTIONS.put(Operation.PLUS, "addition");
        DESCRIPTIONS.put(Operation.MINUS, "subtraction");
        DESCRIPTIONS.put(Operation.TIMES, "multiplication");
        DESCRIPTIONS.put(Operation.DIVIDE, "division");
    }

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return this.symbol;
    }

    public String description() {
        return DESCRIPTIONS.get(this);
    }

    public abstract double apply(double x, double y);

    public static Operation fromSymbol(String s) {
        // switch expressions example
//        return switch(s) {
//            case "+" -> Operation.PLUS;
//            case "-" -> Operation.MINUS;
//            case "*" -> Operation.TIMES;
//            case "/" -> Operation.DIVIDE;
//            default -> throw new IllegalArgumentException("Not a valid operation");
//        };

        // lookup example
        if (BY_SYMBOL.containsKey(s)) {
            return BY_SYMBOL.get(s);
        } else {
            throw new IllegalArgumentException("Not a valid operation");
        }
    }
}
