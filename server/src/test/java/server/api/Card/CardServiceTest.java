package server.api.Card;

import commons.Card;
import commons.CardList;
import commons.Result;
import commons.Task;
import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.List.ListService;
import server.api.Task.TaskService;
import server.database.CardRepository;
import server.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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

    CardList cardList1;
    Task task1;
    Task task2;
    Card card1;
    Card card2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardRepository,taskService);

        cardList1 = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        task1 = new Task( idGenerator1.generateID(), "Test Task", false);
        task2 = new Task( "Test Task", true);
        card1 = new Card(idGenerator1.generateID(),cardList1, "Test Card", "pikachu is cute",
                new ArrayList<>(List.of(task1, task2)), new ArrayList<>());

        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");
        card2 = new Card(idGenerator2.generateID(),cardList1, "Test Card2", "pikachu is cute",
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
    void getAllCardsFAIL(){
        doThrow(new RuntimeException()).when(cardRepository).findAll();

        Result<List<Card>> result = cardService.getAllCards();
        assertEquals(Result.FAILED_GET_ALL_CARDS.of(null), result);
    }

    @Test
    void addNewCard(){
        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.addNewCard(card1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void addNewCardNull(){
        Result<Card> result = cardService.addNewCard(null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }
    @Test
    void addNewCardFAIL(){
        doThrow(new RuntimeException()).when(cardRepository).save(card1);

        Result<Card> result = cardService.addNewCard(card1);
        assertEquals(Result.FAILED_ADD_NEW_CARD.of(null), result);
    }
    @Test
    void deleteCard() {
        Result<Object> result = cardService.deleteCard(card1.cardID);
        assertEquals(Result.SUCCESS.of(null), result);
    }

    @Test
    void deleteCardFAIL() {
        doThrow(new RuntimeException()).when(cardRepository).deleteById(card1.cardID);

        Result<Object> result = cardService.deleteCard(card1.cardID);
        assertEquals(Result.FAILED_DELETE_CARD.of(null), result);
    }

    @Test
    void updateName() {

        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
        doReturn(card1).when(cardRepository).save(card1);

        Result<Object> result = cardService.updateCard(card1,card1.cardID);
        assertEquals(Result.SUCCESS.of(Optional.of(card1)), result);
    }

    @Test
    void updateNameFAIL() {
        doThrow(new RuntimeException()).when(cardRepository).findById(card1.cardID);

        Result<Object> result = cardService.updateCard(card1,card1.cardID);
        assertEquals(Result.FAILED_UPDATE_CARD.of(null), result);
    }

    @Test
    void getCardById() {
        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);

        Result<Card> result = cardService.getCardById(card1.cardID);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void getCardByIdFAIL() {
        doThrow(new RuntimeException()).when(cardRepository).findById(card1.cardID);

        Result<Card> result = cardService.getCardById(card1.cardID);
        assertEquals(Result.FAILED_RETRIEVE_CARD_BY_ID.of(null), result);
    }

    @Test
    void getCardByIdFAILIdNull() {
        Result<Card> result = cardService.getCardById(null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }


    @Test
    void addTaskToCard() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("58");
        Task task = new Task(idGenerator.generateID(),"Task Title",false);

        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.addTaskToCard(task,card1.cardID);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void addTaskToCardFAILNullCase() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("58");
        Task task = new Task(idGenerator.generateID(),"Task Title",false);

        Result<Card> result = cardService.addTaskToCard(task,null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void addTaskToCardFAIL() {
        doThrow(new RuntimeException()).when(cardRepository).findById(card1.cardID);

        Result<Card> result = cardService.addTaskToCard(task1,card2.cardID);
        assertEquals(Result.FAILED_ADD_TASK_TO_CARD.of(null), result);
    }

    @Test
    void removeTaskFromCard() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("58");
        Task task = new Task(idGenerator.generateID(),"Task Title",false);

        doReturn(Optional.of(card1)).when(cardRepository).findById(card1.cardID);
        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.removeTaskFromCard(task,card1.cardID);
        assertEquals(Result.SUCCESS.of(card1), result);

    }

    @Test
    void removeTaskFromCardFAILNull() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("58");
        Task task = new Task(idGenerator.generateID(),"Task Title",false);

        Result<Card> result = cardService.removeTaskFromCard(task,null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }
    @Test
    void removeTaskFromCardFAIL() {
        doThrow(new RuntimeException()).when(taskService).deleteTask(task1.taskID);

        Result<Card> result = cardService.removeTaskFromCard(task1,card1.cardID);
        assertEquals(Result.FAILED_REMOVE_CARD.of(null), result);
    }

    @Test
    void updateCard(){
        doReturn(card1).when(cardRepository).save(card1);

        Result<Card> result = cardService.updateCard(card1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    void updateCardFAIL(){
        doThrow(new RuntimeException()).when(cardRepository).save(card1);

        Result<Card> result = cardService.updateCard(card1);
        assertEquals(Result.FAILED_UPDATE_CARD.of(null), result);
    }
}