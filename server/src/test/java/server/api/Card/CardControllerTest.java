/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api.Card;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import commons.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.Task.TaskService;
import server.database.CardRepository;
import server.database.TaskRepository;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CardControllerTest {


    @Mock
    CardRepository cardRepository;
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskService taskService;
    @Mock
    CardService cardService;
    @InjectMocks
    CardController cardController;

    Card card1;



    @BeforeEach
    public void setUp(){
        //init mocks
        MockitoAnnotations.openMocks(this);
        cardController = new CardController(cardService);
        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
    }


    /**
     * Test for the getAllCards method in the CardController class
     */
    @Test
    public void getAllCardsTest(){

        List<Card> allCards = new ArrayList<Card>();
        allCards.add(card1);
        //Set the mock cardService to return the card when getAllCards is called
        doReturn(Result.SUCCESS.of(allCards)).when(cardService).getAllCards();
        //Call the getAllCards method on the cardController
        Result<List<Card>> result = cardController.getAllCards();
        //Check if the result is the same as the card we created
        assertEquals(Result.SUCCESS.of(allCards), result);
    }

    /**
     * Test for the getCardById method in the CardController class
     */
    @Test
    public void getCardTest(){

        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(Result.SUCCESS.of(card1)).when(cardService).getCardById(1);

        Result<Card> result = cardController.getCardById(1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    public void createNewCardTest() {

        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(Result.SUCCESS.of(card1)).when(cardService).addNewCard(card1);

        Result<Card> result = cardController.createNewCard(card1);

        assertEquals(Result.SUCCESS.of(card1), result);
    }

    //add more tests
    @Test
    public void changeCardNameTest() {

        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(Result.SUCCESS.of(card1)).when(cardService).updateName(card1,1);

        Result<Object> result = cardController.changeCardName(card1,1);

        assertEquals(Result.SUCCESS.of(card1), result);
    }


    @Test
    public void deleteCardTest() {

        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(Result.SUCCESS.of(card1)).when(cardService).deleteCard(1);

        Result<Object> result = cardController.deleteCard(1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    public void removeTaskFromCardTest(){

            Card card1 = new Card(1, "Test Card", "pikachu is cute",
                    new ArrayList<>(), new ArrayList<>());
            Task task = new Task(58, "Test Task",
                    false);
            doReturn(Result.SUCCESS.of(card1)).when(cardService).removeTaskFromCard(task,1);

            Result<Card> result = cardController.removeTaskFromCard(task,1);
            assertEquals(Result.SUCCESS.of(card1), result);
    }
    @Test
    public void addTaskToCardTest() {

        Card card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        Task task = new Task(58, "Test Task",
                false);
        doReturn(Result.SUCCESS.of(card1)).when(cardService).addTaskToCard(task,1);

        Result<Card> result = cardController.addTaskToCard(task,1);
        assertEquals(Result.SUCCESS.of(card1), result);
    }
}
