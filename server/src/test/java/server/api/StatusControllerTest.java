package server.api;

import commons.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StatusControllerTest {

    StatusController controller = new StatusController();

    @Test
    void testConnect() {
        var result = controller.connect();

        assertEquals(Result.SUCCESS, result);
    }
}