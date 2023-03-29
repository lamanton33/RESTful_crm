package server.api;

import commons.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusControllerTest {

    StatusController controller = new StatusController();

    @Test
    void testConnect() {
        var result = controller.connect();

        assertEquals(Result.SUCCESS, result);
    }
}