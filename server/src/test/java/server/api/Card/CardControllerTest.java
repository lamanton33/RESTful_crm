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
import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class CardControllerTest {
    @Mock
    CardService cardService;
    @Mock
    SimpMessagingTemplate msg;
    @InjectMocks
    CardController cardController;

    Card card1;
    CardList cardList1;

    @BeforeEach
    public void setUp(){
        //init mocks
        MockitoAnnotations.openMocks(this);
        cardController = new CardController(cardService, msg);

        cardList1 = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        card1 = new Card(idGenerator1.generateID(),cardList1, "Test Card", "pikachu is cute",
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

        CardList cardList = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        Card card = new Card(idGenerator1.generateID(),cardList, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());

        doReturn(Result.SUCCESS.of(card)).when(cardService).getCardById(idGenerator1.generateID());

        Result<Card> result = cardController.getCardById(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    public void createNewCardTest() {

        CardList cardList = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        Card card = new Card(idGenerator1.generateID(),cardList, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());


        doReturn(Result.SUCCESS.of(card)).when(cardService).addNewCard(card);

        Result<Card> result = cardController.createNewCard(card1);

        assertEquals(Result.SUCCESS.of(card1), result);
    }

    //add more tests
    @Test
    public void changeCardNameTest() {

        CardList cardList = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        Card card = new Card(idGenerator1.generateID(),cardList, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());


        doReturn(Result.SUCCESS.of(card)).when(cardService).updateCard(card,idGenerator1.generateID());

        Result<Object> result = cardController.updateCard(card1,idGenerator1.generateID());

        assertEquals(Result.SUCCESS.of(card1), result);
    }


    @Test
    public void deleteCardTest() {

        CardList cardList = new CardList("Test Card List", new ArrayList<>());

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        Card card = new Card(idGenerator1.generateID(),cardList, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());


        doReturn(Result.SUCCESS.of(card)).when(cardService).deleteCard(idGenerator1.generateID());

        Result<Object> result = cardController.deleteCard(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(card1), result);
    }

    @Test
    public void removeTaskFromCardTest(){
            HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
            idGenerator1.setHardcodedID("1");
            Card card1 = new Card(idGenerator1.generateID(), cardList1, "Test Card", "pikachu is cute",
                    new ArrayList<>(), new ArrayList<>());
            HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
            idGenerator2.setHardcodedID("58");
            Task task = new Task(idGenerator2.generateID(), "Test Task",
                    false);
            doReturn(Result.SUCCESS.of(card1)).when(cardService).removeTaskFromCard(task,idGenerator1.generateID());

            Result<Card> result = cardController.removeTaskFromCard(task,idGenerator1.generateID());
            assertEquals(Result.SUCCESS.of(card1), result);
    }
    @Test
    public void addTaskToCardTest() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        Card card1 = new Card(idGenerator1.generateID(), cardList1, "Test Card", "pikachu is cute",
                new ArrayList<>(), new ArrayList<>());
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("58");
        Task task = new Task(idGenerator2.generateID(), "Test Task",
                false);
        doReturn(Result.SUCCESS.of(card1)).when(cardService).addTaskToCard(task,idGenerator1.generateID());

        Result<Card> result = cardController.addTaskToCard(task,idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(card1), result);
    }
}
