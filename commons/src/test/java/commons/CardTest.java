package commons;

import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CardTest {

    @Test
    void emptyConstructor() {
        Card card = new Card();
        assertEquals(null,card.cardID);
        assertEquals(null,card.cardTitle);
        assertEquals(null,card.cardDescription);
        assertEquals(null,card.taskList);
        assertEquals(null,card.tagList);
        assertEquals(null,card.cardList);
        assertEquals(null,card.cardListId);
    }
    @Test
    void testEquals() {
        Card cardA = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        Card cardB = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        assertEquals(cardA, cardB);
    }

    @Test
    void testNotEquals() {
        Card cardA = new Card(new CardList(), "CardTitleDifferent", "CardDescription", new ArrayList<>(), new ArrayList<>());
        Card cardB = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        assertNotEquals(cardA, cardB);
    }
    @Test
    void testHashCode() {
        Card cardA = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        Card cardB = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        assertEquals(cardA.hashCode(), cardB.hashCode());
    }
    @Test
    void testNotHashCode() {
        Card cardA = new Card(new CardList(), "CardTitleDifferent", "CardDescription", new ArrayList<>(), new ArrayList<>());
        Card cardB = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        assertNotEquals(cardA.hashCode(), cardB.hashCode());
    }
    @Test
    void testToString() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        String actualString = card.toString();
        String string = "Card{cardID=null, cardTitle='CardTitle', cardDescription='CardDescription', taskList=[], tagList=[]}";
        assertEquals(string, actualString);
    }
    @Test
    void testNotToString() {
        Card card = new Card(new CardList(), "CardTitleDifferent", "CardDescription", new ArrayList<>(), new ArrayList<>());
        String actualString = card.toString();
        String string = "Card{cardID=null, cardTitle='CardTitle', cardDescription='CardDescription', cardList=null, tags=[], comments=[]}";
        assertNotEquals(string, actualString);
    }
    @Test
    void setCardTitle() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        card.setCardTitle("CardTitleDifferent");
        assertEquals("CardTitleDifferent", card.cardTitle);
    }
    @Test
    void setCardDescription() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        card.setCardDescription("CardDescriptionDifferent");
        assertEquals("CardDescriptionDifferent", card.cardDescription);
    }
    @Test
    void getCardId() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Card card = new Card(idGenerator.generateID(), new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        assertEquals(idGenerator.generateID(), card.getCardID());
    }
    @Test
    void setCardList() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        CardList cardList = new CardList();
        card.setCardList(cardList);
        assertEquals(cardList, card.cardList);
    }
    @Test
    void setCardListID() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        CardList cardList = new CardList();
        card.setCardListId(cardList.cardListId);
        assertEquals(cardList.cardListId, cardList.cardListId);
    }
    @Test
    void setCardID() {
        Card card = new Card(new CardList(), "CardTitle", "CardDescription", new ArrayList<>(), new ArrayList<>());
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        card.setCardID(idGenerator.generateID());
        assertEquals(idGenerator.generateID(), card.cardID);
    }
}
