package commons;

import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        String string = "Tag{tagID=null, tagTitle='TagTitle', tagColor='red'}";
        assertEquals(string,actualString);
    }

    @Test
    void setTagTitle() {
        Tag tag = new Tag("TagTitle","red");
        tag.setTagTitle("TagTitleDifferent");
        assertEquals("TagTitleDifferent",tag.tagTitle);
    }

    @Test
    void setTagColor() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Tag tag = new Tag(idGenerator.generateID(), "TagTitle","red");
        tag.setTagColor("blue");
        assertEquals("blue",tag.tagColor);
    }
    @Test
    void emptyConstructor() {
        Tag tag = new Tag();
        assertEquals(null,tag.tagID);
        assertEquals(null,tag.tagTitle);
        assertEquals(null,tag.tagColor);
        assertEquals(null,tag.cardId);
        assertEquals(null,tag.card);
    }
    @Test
    void constructorCardDetails() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Card card = new Card(idGenerator.generateID(), new CardList(), "CardDescription", "CardColor",
                new ArrayList<>(), new ArrayList<>());
        Tag tag = new Tag("TagTitle","red", card.cardID, card);
        assertEquals(card.cardID,tag.cardId);
        assertEquals(card,tag.card);
        assertEquals("TagTitle",tag.tagTitle);
        assertEquals("red",tag.tagColor);
    }

    @Test
    void constructorCardDetailsAndTagId() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Card card = new Card(idGenerator.generateID(), new CardList(), "CardDescription", "CardColor",
                new ArrayList<>(), new ArrayList<>());
        Tag tag = new Tag(idGenerator.generateID(), "TagTitle","red", card.cardID, card);
        assertEquals(card.cardID,tag.cardId);
        assertEquals(card,tag.card);
        assertEquals("TagTitle",tag.tagTitle);
        assertEquals("red",tag.tagColor);
        assertEquals(idGenerator.generateID(),tag.tagID);
    }

}