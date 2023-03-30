package server.api.List;

import commons.Card;
import commons.CardList;
import commons.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
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
    @InjectMocks
    ListService listService;

    Card card1;
    CardList list1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listService = new ListService(listRepository, cardService);

        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        List<Card> cardList = new ArrayList<>();
        cardList.add(card1);
        list1 = new CardList(1, "Test List", cardList);
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
        CardList listWithNullTitle = new CardList(1, null, List.of(card1));

        Result<CardList> result = listService.addNewList(listWithNullTitle);
        assertEquals(Result.OBJECT_ISNULL, result);
    }

    @Test
    void addNewCardListNULL() {
        CardList listWithNullCardList = new CardList(1, "Title Card", null);

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
        Result<Object> result = listService.deleteList(1);
        assertEquals(Result.SUCCESS.of(null), result);
    }

    @Test
    void deleteListFAIL() {
        doThrow(new RuntimeException()).when(listRepository).deleteById(null);

        Result<Object> result = listService.deleteList(null);
        assertEquals(Result.FAILED_DELETE_LIST.of(null), result);
    }

    @Test
    void updateName() {

        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListID);
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.updateName(list1,1);
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
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListID);

        Result<CardList> result = listService.getListById(list1.cardListID);
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
        doReturn(Optional.of(list1)).when(listRepository).findById(list1.cardListID);
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.removeCardFromList(card1,1);
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
        CardList listWithEmpyCardList = new CardList(1, "Test List", new ArrayList<>());
        doReturn(Result.SUCCESS.of(card1)).when(cardService).addNewCard(card1);
        doReturn(Optional.of(listWithEmpyCardList)).when(listRepository).findById(1);
        doReturn(list1).when(listRepository).save(list1);

        Result<Card> result = listService.addCardToList(card1,1);
        assertEquals(Result.SUCCESS.of(card1), result);

    }

    @Test
    void addCardToListFAILNullCard() {
        doReturn(Result.OBJECT_ISNULL).when(cardService).addNewCard(null);

        Result<Card> result = listService.addCardToList(null,1);
        assertEquals(Result.OBJECT_ISNULL, result);
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
        CardList listWithEmpyCardList = new CardList(1, "Test List", new ArrayList<>());
        doReturn(Optional.of(list1)).when(listRepository).findById(1);
        doReturn(Optional.of(listWithEmpyCardList)).when(listRepository).findById(2);

        doReturn(listWithEmpyCardList).when(listRepository).save(list1);
        doReturn(list1).when(listRepository).save(listWithEmpyCardList);

        Result<Card> result = listService.moveCard(card1,1, 2, 0);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void moveCardFAILNullCase() {
        Result<Card> result = listService.moveCard(card1,null, 2, 0);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void moveCardFAIL(){
        doThrow(new RuntimeException()).when(listRepository).findById(1);

        Result<Card> result = listService.moveCard(card1,1, 2, 0);
        assertEquals(Result.FAILED_MOVE_CARD.of(null), result);
    }
}