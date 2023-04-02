package commons;

import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
class BoardTest {
    @Test
    void emptyConstructor() {
        Board board = new Board();
        assertEquals(null,board.boardID);
        assertEquals(null,board.boardTitle);
        assertEquals(null,board.cardListList);
        assertEquals(null,board.description);
        assertEquals(null,board.isProtected);
        assertEquals(null,board.passwordHash);
        assertEquals(null,board.boardTheme);
    }
    @Test
    void constructorOne() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals(idGenerator.generateID(),board.boardID);
        assertEquals("BoardTitle",board.boardTitle);
        assertEquals(new ArrayList<>(),board.cardListList);
        assertEquals("Description",board.description);
        assertEquals(false,board.isProtected);
        assertEquals("PasswordHash",board.passwordHash);
        assertEquals(new Theme(),board.boardTheme);
    }
    @Test
    void constructorTwo() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");

        Board board = new Board("BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals("BoardTitle",board.boardTitle);
        assertEquals(new ArrayList<>(),board.cardListList);
        assertEquals("Description",board.description);
        assertEquals(false,board.isProtected);
        assertEquals("PasswordHash",board.passwordHash);
        assertEquals(new Theme(),board.boardTheme);
    }
    @Test
    void addCardList() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board("BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        board.addCardList(new CardList());
        assertEquals(1,board.cardListList.size());
    }
    @Test
    void getCardListList() {
        Board board = new Board("BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        board.addCardList(new CardList());
        assertEquals(List.of(new CardList()),board.getCardListList());
    }
    @Test
    void getCardListByID() {
        Board board = new Board("BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        CardList cardList = new CardList(idGenerator.generateID(), "CardListTitle", new ArrayList<>(), board);
        board.addCardList(cardList);
        assertEquals(cardList,board.getCardListByID(idGenerator.generateID()));
    }
    @Test
    void getBoardID() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals(idGenerator.generateID(),board.getBoardID());
    }
    @Test
    void setBoardID() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        board.setBoardID(idGenerator.generateID());
        assertEquals(idGenerator.generateID(),board.boardID);
    }
    @Test
    void setBoardTheme() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        board.setBoardTheme(new Theme());
        assertEquals(new Theme(),board.boardTheme);
    }
    @Test
    void equals() {
        Board board1= new Board(null, "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        Board board2 = new Board(null, "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals(board1,board2);
    }
    @Test
    void notEquals() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        Board board1 = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertNotEquals(board,board1);
    }
    @Test
    void hashCodeTest() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        Board board1 = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals(board.hashCode(),board1.hashCode());
    }
    @Test
    void hashCodeNotTest() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle1", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        Board board1 = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertNotEquals(board.hashCode(),board1.hashCode());
    }
    @Test
    void toStringTest() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Board board = new Board(idGenerator.generateID(), "BoardTitle", new ArrayList<>(), "Description", false, "PasswordHash", new Theme());
        assertEquals("Board{boardID=" + idGenerator.generateID() + ", boardTitle='BoardTitle', cardListList=[], description='Description', isProtected=false, passwordHash='PasswordHash', boardTheme=Theme{themeID=null, backgroundColor='null', cardColor='null', textColor='null'}}",board.toString());
    }
}