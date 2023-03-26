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
import server.api.Card.CardService;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

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
        list1 = new CardList(1, "Test List", List.of(card1));
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
    void addNewList() {

        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.addNewList(list1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void deleteList() {
        Result<Object> result = listService.deleteList(1);
        assertEquals(Result.SUCCESS.of(null), result);
    }

    @Test
    void updateName() {

        doReturn(Optional.of(list1)).when(listRepository).findById(1);
        doReturn(list1).when(listRepository).save(list1);

        Result<CardList> result = listService.updateName(list1,1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

    @Test
    void getListById() {
        doReturn(Optional.of(list1)).when(listRepository).findById(1);

        Result<CardList> result = listService.getListById(1);
        assertEquals(Result.SUCCESS.of(list1), result);
    }

//    @Test
//    void removeCardFromList() {
//        doReturn(Optional.of(list1)).when(listRepository).findById(1);
//        doReturn(list1).when(listRepository).save(list1);
//        doReturn(null).when(cardService).deleteCard(1);
//
//        Result<CardList> result = listService.removeCardFromList(card1,1);
//        assertEquals(Result.SUCCESS.of(list1), result);
//    }

    @Test
    void addCardToList() {
    }
}