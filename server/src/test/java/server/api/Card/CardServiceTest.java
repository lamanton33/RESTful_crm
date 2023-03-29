package server.api.Card;

import commons.Card;
import commons.Result;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.Task.TaskService;
import server.database.CardRepository;
import server.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {


    @Mock
    CardRepository cardRepository;
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskService taskService;
    @InjectMocks
    CardService cardService;

    Card card1;
    Card card2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardRepository,taskService);

        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        card2 = new Card(2, "Test Card2", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void getAllCards(){

        List<Card> allCards = new ArrayList<Card>();
        allCards.addAll(List.of(card1,card2));

        doReturn(List.of(card1,card2)).when(cardRepository).findAll();

        Result<List<Card>> result = cardService.getAllCards();
        assertEquals(Result.SUCCESS.of(allCards), result);

    }

    @Test
    void addNewCard(){

        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.addNewCard(card1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void deleteCard() {

        Result<Object> result = cardService.deleteCard(card1.cardID);
        assertEquals(Result.SUCCESS.of(null), result);
    }

    @Test
    void updateName() {

        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
        doReturn(card1).when(cardRepository).save(card1);

        Result<Object> result = cardService.updateCard(card1,card1.cardID);
        assertEquals(Result.SUCCESS.of(Optional.of(card1)), result);
    }

    @Test
    void getCardById() {
        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);

        Result<Card> result = cardService.getCardById(card1.cardID);
        assertEquals(Result.SUCCESS.of(card1), result);
    }


//    @Test
//    void addTaskToCard() {
//        Task task = new Task(58,"Task Title",false);
//
//        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
//        doReturn(card1).when(cardRepository).save(card1);
//
//        Result<Card> result = cardService.addTaskToCard(task,card1.cardID);
//        assertEquals(Result.SUCCESS.of(card1), result);
//    }

    @Test
    void removeTaskFromCard() {
        Task task = new Task(58,"Task Title",false);

        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.removeTaskFromCard(task,card1.cardID);
        assertEquals(Result.SUCCESS.of(card1), result);

    }

}