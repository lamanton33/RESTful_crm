package server.api.List;

import commons.Card;
import commons.CardList;
import commons.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class ListControllerTest {

    @Mock
    ListService listService;

    @InjectMocks
    ListController listController;

    Card card1;
    CardList list1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listController = new ListController(listService);

        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        list1 = new CardList(1, "Test List", List.of(card1));
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

        doReturn(Result.SUCCESS.of(list1)).when(listService).getListById(1);

        Result<CardList> result = listController.getListById(1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void createNewList() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).addNewList(list1);

        Result<CardList> result = listController.createNewList(list1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void deleteList() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).deleteList(1);

        Result<Object> result = listController.deleteList(1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void updateName() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).updateName(list1, 1);

        Result<CardList> result = listController.updateName(list1,1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void removeCardFromList() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).removeCardFromList(card1, 1);

        Result<CardList> result = listController.removeCardFromList(card1, 1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void addCardToList() {
        doReturn(Result.SUCCESS.of(card1)).when(listService).addCardToList(card1, 1);

        Result<Card> result = listController.addCardToList(card1, 1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void moveCard() {
        doReturn(Result.SUCCESS.of(list1)).when(listService).removeCardFromList(card1, 1);
        doReturn(Result.SUCCESS.of(card1)).when(listService).addCardToList(card1, 2);

        Result<Card> result = listController.moveCard(card1, 1, 2);
        assertEquals(Result.SUCCESS.of(card1), result);
    }
}