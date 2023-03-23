package commons;

import java.util.*;

public class Result<T> {
    public boolean success;
    public int code;
    public String message;
    public T value;

    private Result() {
    }

    protected Result(int code, String message, boolean success, T value) {
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
    public static final Result<Object> OBJECT_ISNULL = new Result<>(
            2,
            "Given object is null.", false, null);
    public static final Result<Object> FAILED_ADD_NEW_CARD = new Result<>(
            3,
            "Failed to add new card.", false, null);
    public static final Result<Object> FAILED_DELETE_CARD = new Result<>(
            4,
            "Failed to delete card.", false, null);
    public static final Result<Object> FAILED_UPDATE_CARD = new Result<>(
            5,
            "Failed to update card name.", false, null);
    public static final Result<Card> FAILED_RETRIEVE_CARD_BY_ID = new Result<>(
            6,
            "Failed to retrieve card by id.", false, null);
    public static final Result<List<CardList>> FAILED_GET_ALL_LIST = new Result<>(
            7,
            "Failed to retrieve all lists.", false, null);
    public static final Result<CardList> FAILED_ADD_NEW_LIST = new Result<>(
            8,
            "Failed to add new list.", false, null);
    public static final Result<Object> FAILED_DELETE_LIST = new Result<>(
            9,
            "Failed to delete list.", false, null);
    public static final Result<CardList> FAILED_UPDATE_LIST = new Result<>(
            10,
            "Failed to update list name.", false, null);
    public static final Result<CardList> FAILED_RETRIEVE_LIST_BY_ID = new Result<>(
            11,
            "Failed to retrieve list by given id.", false, null);
    public static final Result<List<Card>> FAILED_GET_ALL_CARDS = new Result<>(
            12,
            "Failed to retrieve all cards.", false, null);
    public static final Result<Card> FAILED_REMOVE_CARD = new Result<>(
                13,
                "Failed to remove card.", false, null);
    public static final Result<Card> FAILED_ADD_TASK_TO_CARD = new Result<>(
                14,
                "Failed to add task to card.", false, null);
    public static final Result<List<Task>> FAILED_GET_ALL_TASKS = new Result<>(
                15,
                "Failed to retrieve all tasks.", false, null);
    public static final Result<Task> FAILED_ADD_NEW_TASK = new Result<>(
                16,
                "Failed to add new task.", false, null);
    public static final Result<Task> FAILED_UPDATE_TASK = new Result<>(
                17,
                "Failed to update task attributes.", false, null);
    public static final Result<Task> FAILED_RETRIEVE_TASK_BY_ID = new Result<>(
                18,
                "Failed to retrieve task by ID.", false, null);
    public static final Result<Task> FAILED_TOGGLE_TASK_COMPLETED_STATUS = new Result<>(
                19,
                "Failed to toggle task completion status.", false, null);

    public static final Result<Object> FAILED_WEBSOCKET_CONNECTION = new Result<>(
            1,
            "Error while trying to connect to websocket", false, null);
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
