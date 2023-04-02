package server.api.List;

import commons.*;
import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@ExtendWith(MockitoExtension.class)
class ListControllerTest {

    @Mock
    ListService listService;
    @Mock
    SimpMessagingTemplate msg;
    @InjectMocks
    ListController listController;

    Card card1;
    CardList list1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listController = new ListController(listService, msg);

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        //String boardTitle,
        //                 List<CardList> cardListList,
        //                 String description,
        //                 Boolean isProtected,
        //                 String passwordHash,
        //                 Theme boardTheme
        list1 = new CardList(idGenerator1.generateID(), "Test List",
                new ArrayList<>(), new Board("Test Board", new ArrayList<>(),
                            "Test Description", false, "Test Password", new Theme()));
        card1 = new Card(idGenerator1.generateID(),list1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        list1.addCard(card1);
    }

    @Test
    void getAllLists() {
        List<CardList> allLists = new ArrayList<>();
        allLists.add(list1);
        doReturn(Result.SUCCESS.of(allLists)).when(listService).getAll();

        Result<List<CardList>> result = listController.getAllLists();
        assertEquals(Result.SUCCESS.of(allLists), result);
    }

    @Test
    void getListById() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(list1)).when(listService).getListById(idGenerator1.generateID());

        Result<CardList> result = listController.getListById(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void createNewList() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).addNewList(list1);

        Result<CardList> result = listController.createNewList(list1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }
    @Test
    void createNewListFail() {
        doReturn(Result.FAILED_ADD_NEW_LIST).when(listService).addNewList(list1);

        Result<CardList> result = listController.createNewList(list1);
        assertEquals(Result.FAILED_ADD_NEW_LIST, result);
    }

    @Test
    void deleteList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(list1)).when(listService).deleteList(idGenerator1.generateID());

        Result<Object> result = listController.deleteList(idGenerator1.generateID(), list1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void updateName() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(list1)).when(listService).updateName(list1, idGenerator1.generateID());

        Result<CardList> result = listController.updateName(list1,idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void removeCardFromList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(list1)).when(listService).removeCardFromList(card1, idGenerator1.generateID());

        Result<CardList> result = listController.removeCardFromList(card1, idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void addCardToList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(card1)).when(listService).addCardToList(card1, idGenerator1.generateID());

        Result<Card> result = listController.addCardToList(card1, idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void moveCard() {
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");
        CardList listWithEmpyCardList = new CardList(idGenerator2.generateID(), "Test List",
                new ArrayList<>(), new Board());
        doReturn(Result.SUCCESS.of(listWithEmpyCardList)).when(listService).getListById(idGenerator2.generateID());
        doReturn(Result.SUCCESS.of(card1)).when(listService).moveCard(card1, list1.cardListId, idGenerator2.generateID(), 0);

        Result<Card> result = listController.moveCard(card1, list1.cardListId, idGenerator2.generateID(), 0);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void moveCardFAIL() {
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");

//        doThrow(new RuntimeException()).when(listService).getListById(idGenerator2.generateID());
        doReturn(Result.FAILED_RETRIEVE_LIST_BY_ID.of(null)).when(listService).getListById(idGenerator2.generateID());

        Result<Card> result = listController.moveCard(card1, list1.cardListId, idGenerator2.generateID(), 0);
        assertEquals(Result.FAILED_MOVE_CARD.of(null), result);
    }
}