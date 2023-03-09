package commons;

import java.util.*;

public class Result<T> {
    public boolean success;
    public int code;
    public String message;
    public T value;

    private Result() {
    }

    public Result(int code, String message, boolean success, T value) {
        this.message = message;
        this.success = success;
        this.code = code;
        this.value = value;
    }

    /** Used to add an object to a result preset. */
    public <U> Result<U> of(U value) {
        return new Result<>(code, message, success, value);
    }

    public static final Result<Object> SUCCESS = new Result<>(
            0,
            "Operation was successful", true, null);
    public static final Result<Object> BOARD_DOESNT_EXIST = new Result<>(
            1,
            "The specified board does not exist.", false, null);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result<?> result = (Result<?>) o;
        return success == result.success
                && code == result.code
                && message.equals(result.message)
                && Objects.equals(value, result.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, code, message, value);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", value=" + value +
                '}';
    }
}
