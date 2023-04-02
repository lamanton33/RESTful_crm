package server.api.List;

import commons.*;
import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.Board.BoardService;
import server.api.Card.CardService;
import server.api.Task.TaskService;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ListServiceTest {

    @Mock
    ListRepository listRepository;
    @Mock
    CardService cardService;
    @Mock
    BoardService boardService;
    @InjectMocks
    ListService listService;

    Board board1;
    Card card1;
    CardList list1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listService = new ListService(listRepository, cardService, boardService);

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        list1 = new CardList(idGenerator1.generateID(), "Test List",
                             new ArrayList<>(), new Board());
        board1 = new Board(idGenerator1.generateID(), "Test Board", List.of(list1),
                "Test Description", false, "Test Password", new Theme());
        card1 = new Card(idGenerator1.generateID(),list1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        list1.addCard(card1);

    }

    @Test
    void getAll() {
        List<CardList> cardListList = new ArrayList<>();
        cardListList.add(list1);

        doReturn(cardListList).when(listRepository).findAll();

        Result<List<CardList>> result = listService.getAll();
        assertEquals(Result.SUCCESS.of(cardListList), result);
    }
    @Test
    void getAllFAIL() {
        doThrow(new RuntimeException()).when(listRepository).findAll();

        Result<List<CardList>> result = listService.getAll();
        assertEquals(Result.FAILED_GET_ALL_LIST, result);
    }

    @Test
    void addNewList() {
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.addNewList(list1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }
    @Test
    void addNewListTitleNULL() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        CardList listWithNullTitle = new CardList(idGenerator1.generateID(), null,
                new ArrayList<>(), new Board());
        Result<CardList> result = listService.addNewList(listWithNullTitle);
        assertEquals(Result.OBJECT_ISNULL, result);
    }

    @Test
    void addNewCardListNULL() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        CardList listWithNullCardList = new CardList(idGenerator1.generateID(), "Test List",
                                                  null, new Board());
        Result<CardList> result = listService.addNewList(listWithNullCardList);
        assertEquals(Result.OBJECT_ISNULL, result);
    }

    @Test
    void addNewListFAIL() {
        doThrow(new RuntimeException()).when(listRepository).save(list1);

        Result<CardList> result = listService.addNewList(list1);
        assertEquals(Result.FAILED_ADD_NEW_LIST, result);
    }

    @Test
    void deleteList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        Board boardWithoutList = new Board(idGenerator1.generateID(), "Test Board", new ArrayList<>(),
                "Test Description", false, "Test Password", new Theme());
        doReturn(Result.SUCCESS.of(boardWithoutList)).when(boardService).deleteList(list1);
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListId);

        Result<Object> result = listService.deleteList(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(true), result);
    }

    @Test
    void deleteListFAILNullId() {
        Result<Object> result = listService.deleteList(null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }
    @Test
    void deleteListFAIL() {
        doThrow(new RuntimeException()).when(listRepository).findById(null);

        Result<Object> result = listService.deleteList(list1.cardListId);
        assertEquals(Result.FAILED_DELETE_LIST.of(false), result);
    }

    @Test
    void updateName() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListId);
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.updateName(list1,idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void updateNameFAIL() {
        doThrow(new RuntimeException()).when(listRepository).findById(null);

        Result<CardList> result = listService.updateName(list1,null);
        assertEquals(Result.FAILED_UPDATE_LIST.of(null), result);
    }

    @Test
    void getListById() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListId);

        Result<CardList> result = listService.getListById(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void getListByIdFAIL() {
        doThrow(new RuntimeException()).when(listRepository).findById(null);

        Result<CardList> result = listService.getListById(null);
        assertEquals(Result.FAILED_RETRIEVE_LIST_BY_ID.of(null), result);
    }

    @Test
    void removeCardFromList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListId);
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.removeCardFromList(card1,idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(list1), result);
    }
    @Test
    void removeCardFromListFAIL() {
        doThrow(new RuntimeException()).when(listRepository).findById(null);

        Result<CardList> result = listService.removeCardFromList(card1,null);
        assertEquals(Result.FAILED_REMOVE_CARD_FROM_LIST.of(null), result);
    }
    @Test
    void addCardToList() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        CardList listWithEmpyCardList = new CardList(idGenerator1.generateID(), "Test List",
                                              new ArrayList<>(), new Board());
        doReturn(Result.SUCCESS.of(card1)).when(cardService).addNewCard(card1);
        doReturn(Optional.of(listWithEmpyCardList)).when(listRepository).findById(idGenerator1.generateID());
        doReturn(list1).when(listRepository).save(list1);

        Result<Card> result = listService.addCardToList(card1,idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(card1), result);

    }

    @Test
    void addCardToNotValidList() {
        //test if list is not valid
        doThrow(new RuntimeException()).when(listRepository).findById(null);

        Result<Card> result = listService.addCardToList(card1,null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void moveCard() {
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");
        CardList listWithEmpyCardList =  new CardList(idGenerator2.generateID(), "Test List",
                new ArrayList<>(), new Board());

        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListId);
        doReturn(Optional.of(listWithEmpyCardList)).when(listRepository).findById(idGenerator2.generateID());

        doReturn(listWithEmpyCardList).when(listRepository).save(list1);
        doReturn(list1).when(listRepository).save(listWithEmpyCardList);

        Result<Card> result = listService.moveCard(card1,list1.cardListId, listWithEmpyCardList.cardListId, 0);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void moveCardFAILNullCase() {
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("1");
        Result<Card> result = listService.moveCard(card1,null, idGenerator2.generateID(), 0);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void moveCardFAIL(){
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");
        doThrow(new RuntimeException()).when(listRepository).findById(list1.cardListId);

        Result<Card> result = listService.moveCard(card1,list1.cardListId, idGenerator2.generateID(), 0);
        assertEquals(Result.FAILED_MOVE_CARD.of(null), result);
    }
}