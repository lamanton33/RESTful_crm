package commons.utils;


import java.util.UUID;

public class HardcodedIDGenerator implements IDGenerator {
    public String hardcodedID = "HARDCODED";

    public void setHardcodedID(String hardcodedID) {
        this.hardcodedID = hardcodedID;
    }

    @Override
    public UUID generateID() {
        return UUID.nameUUIDFromBytes(hardcodedID.getBytes());
    }
}
