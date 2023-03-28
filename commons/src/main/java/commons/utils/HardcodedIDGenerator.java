package commons.utils;


import java.util.UUID;

public class HardcodedIDGenerator implements IDGenerator {
    @Override
    public UUID generateID() {
        return UUID.nameUUIDFromBytes("HARDCODED".getBytes());
    }
}
