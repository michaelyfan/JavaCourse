package record;

public sealed interface Result<T> permits Success, Failure {

    /**
     * From Claude:
     *
     * A snag you'll run into:
     * Failure not being parameterized means you can't directly assign a Failure to a Result<String>.
     * The compiler will complain about the missing type argument.
     * The fix is to make Failure implement Result<T> for any T: declare it as
     * record Failure(Throwable error) implements Result<Object> {} won't work either,
     * because then it can't be a Result<String>. The idiomatic Java solution is to have
     * a small generic factory method on the Result interface that returns the right type, e.g.:
     */
    static <T> Result<T> failure(Throwable error) {
        return new Failure(error);
    }

    default T unwrapOrThrow() {
        return switch(this) {
            Success<T> s -> s.value();
            Failure f -> throw new RuntimeException(f.error());
        };
    }
}

record Success<T>(T value) implements Result<T> {}
record Failure(Throwable error) implements Result {}
