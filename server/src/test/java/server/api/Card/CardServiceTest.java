package server.api.Card;

import commons.Card;
import commons.Result;
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


    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardRepository,taskService);
    }

    @Test
    void getAllCards() {
        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        Card card2 = new Card(2, "Test Card2", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        List<Card> allCards = new ArrayList<Card>();
        allCards.addAll(List.of(card1,card2));

        doReturn(List.of(card1,card2)).when(cardRepository).findAll();

        Result<List<Card>> result = cardService.getAllCards();
        assertEquals(Result.SUCCESS.of(allCards), result);

    }

    @Test
    void addNewCard() {
        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.addNewCard(card1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void deleteCard() {
        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(card1).when(cardRepository).delete(card1);

        Result<Card> result = cardService.addNewCard(card1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void updateName() {
    }

    @Test
    void getCardById() {
    }

    @Test
    void removeTaskFromCard() {
    }

    @Test
    void addTaskToCard() {
    }
}