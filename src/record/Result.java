package record;

public sealed interface Result<T> permits Success, Failure {

    static <T> Result<T> success(T value)         { return new Success<>(value); }
    static <T> Result<T> failure(Throwable error) { return new Failure<>(error); }

    default T unwrapOrThrow() {
        return switch(this) {
            case Success<T> s -> s.value();
            case Failure<T> f -> throw new RuntimeException(f.error());
        };
    }
}

record Success<T>(T value) implements Result<T> {}
record Failure<T>(Throwable error) implements Result<T> {}
