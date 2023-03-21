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
package client.utils;

import commons.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.client.*;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;


import static jakarta.ws.rs.core.MediaType.*;

public class ServerUtils {

    private String serverUrl;
    private StompSession session;

    /** Sets the server connection string. */
    public void setServer(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /** Generic get request handler */
    public Object get(String url) {
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(url)
                        .request(APPLICATION_JSON)
                        .get().getEntity();
    }

    /** Generic post request handler */
    public <T> T post(String url, Object payload) {
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(url)
                        .request(APPLICATION_JSON)
                        .post(Entity.entity(payload, APPLICATION_JSON), new GenericType<>() {});
    }
    /** Generic put request handler */
    public <T> T put(String url, Object payload) {
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(url)
                        .request(APPLICATION_JSON)
                        .put(Entity.entity(payload, APPLICATION_JSON), new GenericType<>() {});
    }


    /** Tries to "connect" to a server by trying to see if the server exists. */
    public Result<Object> connect() {
        return  new Result<>(0,"Operation Successful",true,
                this.get("api/status"));
    }

    /**
     * Post request to add the CardList list to the server repository
     */
    public Result<CardList> addList(CardList list) {
        return new Result<>(0,"Operation Successful",true,
                this.post("api/list/", list)) ;
    }


    /**
     * Get request to get all the CardLists from the server repository
     */
    @SuppressWarnings("unchecked")
    public Result<List<CardList>> getLists() {
        return new Result<>(0,"Operation Successful",true,
                (List<CardList>) this.get("api/list/"));
    }


    /**
     * Delete request to delete the CardList with given id from the server repository
     */
    public Result<CardList> deleteList(Integer listId) {
        return new Result<>(0,"Operation Successful",true,
                this.post("api/list/" + listId,listId)) ;
    }

    /**
     * Put request to update the CardList with given id to the CardList list
     */
    public Result<CardList> editList(CardList list, Integer listId) {
        return new Result<>(0,"Operation Successful",true,
                this.put("api/list/" + listId, list));
    }

    /**
     * Put request to remove the card in the request body
     * from the list with the given id
     */
    public Result<CardList> removeCardFromList(Integer listId, Card card){
        return new Result<>(0,"Operation Successful",true,
                this.put("api/list/deleteCard/" + listId, card));
    }

    /**
     * Put request to add the card in the request body
     * to the list with the given id
     */
    public Result<Card> addCardToList(Card card, Integer listId){
        return new Result<>(0,"Operation Successful",true,
                this.put("api/list/addCard/" + listId, card));
    }

    /**
     * Put request to move the card in the request body
     * from the list with id = id_from
     * to the list with id = id_to
     */
    public Result<CardList> moveCardBetweenLists(Card card, Integer cardIdFrom, Integer cardIdTo){
        return new Result<>(0,"Operation Successful",true,
                this.put("api/list/moveCard/" + cardIdFrom + "/" + cardIdTo, card));
    }

    /**
     * Post request to add the Card to the server repository
     */
    public Result<Card> addCard(Card card) {
        return new Result<>(0,"Operation Successful",true,
                this.put("api/card/", card));

    }

    /**
     * Put request to add the task in the request body
     * to the card with the given id
     */
    public Result<Card> addTaskToCard(Task task, Integer cardId){
        return new Result<>(0,"Operation Successful",true,
                this.put("api/card/addTask/" + cardId, task));
    }

    /**
     * Put request to remove the task in the request body
     * from the card with the given id
     */
    public Result<Card> removeTaskFromCard(Task task, Integer cardId){
        return new Result<>(0,"Operation Successful",true,
                this.put("api/card/removeTask/" + cardId, task));
    }


    /** start the websocket. */
    public Result<Object> startWebsocket() {
        this.session = (StompSession) get("ws:" + serverUrl +"/websocket");
        return new Result<Object>(0,"Operation Successful",true,null);
    }

    public StompSession connectStomp(String url){
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            StompSession stompSession = stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
            return stompSession;

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer){
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            @SuppressWarnings("unchecked")
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Retrieves a complete Board object from the server
     */
    public Result getBoard() {
        return new Result<>(0,"Operation Successful",true,
                (Board) this.get("api/board/"));
    }
}