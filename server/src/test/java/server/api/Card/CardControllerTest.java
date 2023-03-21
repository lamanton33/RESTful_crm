//package server.api.Card;
//
//
//import commons.Card;
//import commons.Result;
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//import server.database.CardRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@WebMvcTest(CardController.class)
//@RunWith(MockitoJUnitRunner.class)
//public class CardControllerTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    @InjectMocks
//    CardController cardController;
//
//    Card card;
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//
//        cardController = new CardController(cardService);
//
//        this.card = new Card(58, "Test Card",
//                "pikachu is cute", new ArrayList<>(),
//                new ArrayList<>());
//    }
//
//    @Test
//    public void getAllCardsTest(){
//        Card card1 = new Card("Test Card");
//        List<Card> allCards = new ArrayList<Card>();
//        allCards.add(card1);
//
//        cardController.createNewCard(new Card("Test Card"));
//        mvc.perform()
//        assertEquals(Result.SUCCESS.of(allCards),cardController.getAllCards());
//    }
//
//
//    @Test
//    public void createNewCardTest(){
//    }
//}

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

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.api.TestClasses.TestCardRepository;

public class CardControllerTest {

    private TestCardRepository repo;
    private CardService cardService;
    private CardController cardController;

//    @BeforeEach
//    public void setup() {
//        repo = new TestCardRepository();
//        cardService = new CardService(repo);
//        cardController = new CardController(cardService);
//    }
//
//    @Test
//    public void cannotAddNullCard() {
//        Result actual = cardController.createNewCard(null);
//        assertEquals(-1, actual.code);
//    }
//
//    @Test
//    public void databaseIsUsed() {
//        cardController.createNewCard(new Card("Test Card"));
//        repo.calledMethods.contains("save");
//    }

//    @Test
//    public void getAllCardsTest(){
//        Card card1 = new Card("Test Card1");
//        Card card2 = new Card("Test Card2");
//
//        List<Card> allCards = new ArrayList<Card>();
//        allCards.add(card1);
//        allCards.add(card2);
//
//        cardController.createNewCard(new Card("Test Card1"));
//        cardController.createNewCard(new Card("Test Card2"));
//        assertEquals(Result.SUCCESS.of(allCards),cardController.getAllCards());
//    }




}
