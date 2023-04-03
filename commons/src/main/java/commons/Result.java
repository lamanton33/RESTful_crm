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
            "Failed to update card attributes.", false, null);
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
    public static final Result<CardList> FAILED_REMOVE_CARD_FROM_LIST = new Result<>(
            14,
            "Failed to remove card from list.", false, null);
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
    public static final Result<List<Board>> FAILED_DELETE_TASK = new Result<>(
            21,
            "Failed to delete task.", false, null);
    public static final Result<List<Board>> FAILED_GET_ALL_BOARDS = new Result<>(
            22,
            "Failed to retrieve all boards.", false, null);
    public static final Result<Board> FAILED_ADD_NEW_BOARD = new Result<>(
            23,
            "Failed to add new board.", false, null);
    public static final Result<Board> FAILED_DELETE_BOARD = new Result<>(
            24,
            "Failed to delete board.", false, null);
    public static final Result<Board> FAILED_UPDATE_BOARD_THEME = new Result<>(
            25,
            "Failed to update board theme.", false, null);
    public static final Result<CardList> FAILED_RETRIEVE_BOARD_BY_ID = new Result<>(
            26,
            "Failed to retrieve board by given id.", false, null);
    public static final Result<Card> FAILED_MOVE_CARD = new Result<>(
            27,
            "Failed to move card.", false, null);
    public static final Result<Board> FAILED_TO_GET_BOARD_BY_ID = new Result<>(
            28,
            "Failed to update board theme.", false, null);

    public static final Result<Object> FAILED_WEBSOCKET_CONNECTION = new Result<>(
            29,
            "Error while trying to connect to websocket", false, null);

    public static final Result<Board> FAILED_TO_ADD_LIST_TO_BOARD = new Result<>(
            30,
            "Failed to add a new list to the board.", false, null);

    public static final Result<Tag> FAILED_UPDATE_TAG = new Result<>(
            31,
            "Failed to add update the tag.", false, null);

    public static final Result<Tag> FAILED_UPDATE_BOARD = new Result<>(
            32,
            "Failed to update the board.", false, null);

    public static final Result<Object> FAILED_TO_CONNECT_TO_SERVER = new Result<>(
            34,
            "Error while trying to connect to the server", false, null);




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
