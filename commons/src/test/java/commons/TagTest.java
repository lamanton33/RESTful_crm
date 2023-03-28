package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void testEquals() {
        Tag tagA = new Tag("TagTitle","Red");
        Tag tagB = new Tag("TagTitle","Red");
        Tag tagC = new Tag("TagTitleDifferent","Blue");

        assertEquals(tagA,tagB);
        assertNotEquals(tagB,tagC);
    }

    @Test
    void testHashCode() {
        Tag tagA = new Tag("TagTitle","red");
        Tag tagB = new Tag("TagTitle","red");
        Tag tagC = new Tag("TagTitleDifferent","blue");
        assertEquals(tagA.hashCode(),tagB.hashCode());
        assertNotEquals(tagB.hashCode(),tagC.hashCode());
    }

    @Test
    void testToString() {
        Tag tag = new Tag("TagTitle","red");
        String actualString = tag.toString();
        String string = "Tag{tagID=1, tagTitle='TagTitle', tagColor='red'}";
        assertEquals(string,actualString);
    }
}