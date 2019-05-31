package kk.sudoku;

public class Result<T, E extends Enum<E>> {
    private T value;
    private E error;

    private Result() {
    }

    public static <T, E extends Enum<E>> Result<T, E> error(E error) {
        Result<T, E> result = new Result<>();
        result.error = error;

        return result;
    }

    public static <T, E extends Enum<E>> Result<T, E> value(T value) {
        Result<T, E> result = new Result<>();
        result.value = value;

        return result;
    }

    public T getValue() {
        return value;
    }

    public boolean hasError() {
        return error != null;
    }

    public E getError() {
        return error;
    }

    public String getErrorMessage() {
        return error != null ? error.name() : null;
    }
}
