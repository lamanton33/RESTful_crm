package commons.utils;


import java.util.UUID;

public class HardcodedIDGenerator implements IDGenerator {
    public String hardcodedID = "HARDCODED";

    /**
     * @param hardcodedID the hardcodedID to set
     */
    public void setHardcodedID(String hardcodedID) {
        this.hardcodedID = hardcodedID;
    }

    /**
     * @return the generated ID
     */
    @Override
    public UUID generateID() {
        return UUID.nameUUIDFromBytes(hardcodedID.getBytes());
    }
}
