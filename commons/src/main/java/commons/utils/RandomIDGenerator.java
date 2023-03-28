package commons.utils;

import java.util.UUID;

public class RandomIDGenerator implements IDGenerator {
    @Override
    public UUID generateID() {
        return UUID.randomUUID();
    }
}
