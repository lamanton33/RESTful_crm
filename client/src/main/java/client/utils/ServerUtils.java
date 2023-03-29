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
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private String serverUrl;
    private StompSession session;


    //Getters - Setters

    /** setter for serverUrl
     * @param serverUrl
     */
    public void setServer(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /** getter for serverUrl
     * @return serverUrl String
     */
    public String getServerUrl(){
        return serverUrl;
    }

    /** sets the stompSession from the connectionHandler
     * @param stompSession websocket session
     */
    public void setSession(StompSession stompSession) {
        this.session = stompSession;
    }

    // Helper functions for dealing with HTTP

    /**Generic get request handler
     * @param dest destination endpoint
     * @return result Result Object containing status and a payload from the server
     */
    private <T> Result<T> get(String dest) {
        System.out.println("Sending a GET request to " + dest);
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(dest)
                        .request(APPLICATION_JSON)
                        .get(new GenericType<>() {});
    }

    /**Generic post request handler
     * @param dest destination endpoint
     * @param payload payload from the client
     * @return result Result Object containing status and an empty payload
     */
    private <T> Result<T> post(String dest, Object payload) {
        System.out.println("Sending a POST request to " + dest);
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(dest)
                        .request(APPLICATION_JSON)
                        .post(Entity.entity(payload, APPLICATION_JSON), new GenericType<>() {});
    }

    /**Generic put request handler
     * @param dest destination endpoint
     * @param payload payload from the client
     * @return result Result Object containing status and an empty payload
     */
    private <T> Result<T> put(String dest, Object payload) {
        System.out.println("Sending a PUT request to " + dest);
        return ClientBuilder.newClient(new ClientConfig())
                        .target(serverUrl).path(dest)
                        .request(APPLICATION_JSON)
                        .put(Entity.entity(payload, APPLICATION_JSON), new GenericType<>() {});
    }

    /**Generic delete request handler
     * @param dest destination endpoint
     * @return result Result Object containing status and an empty payload
     */
    public <T> Result<T> delete(String dest) {
        System.out.println("Sending a DELETE request to " + dest);
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverUrl).path(dest)
                .request(APPLICATION_JSON)
                .delete(new GenericType<>() {});
    }


    /** Tries to "connect" to a server by trying to see if the server exists.
     * @return Result Object containing status and an empty payload
     */
    public Result<Object> connect() {
        return this.get("api/status/");
    }

    /**
     * Post request to add the CardList list to the server repository
     * @return Result Object containing status and an empty payload
     */
    public Result<CardList> addList(CardList list) {
        return this.post("api/list/create/", list);
    }


    /**
     * Get request to get all the CardLists from the server repository
     * @return Result Object containing status and an empty payload
     */
    public Result<List<CardList>> getLists() {
        return this.get("api/list/get-all/");
    }


    /**
     * Delete request to delete the CardList with given id from the server repository
     * @param listId id of the list to be deleted
     * @return Result Object containing status and an empty payload
     */
    public Result<CardList> deleteList(UUID listId) {
        return this.delete("api/list/delete/" + listId);
    }

    /**
     * Put request to update the CardList with given id to the CardList list
     * @param list list Object that needs to be updated on the server
     * @param listId id of that list
     * @return Result Object containing status and an empty payload
     */
    public Result<CardList> editList(CardList list, UUID listId) {
        //TODO make it work by only using the ID
        return this.put("api/list/update/" + listId, list);
    }

    /**
     * Put request to remove the card in the request body
     * from the list with the given id
     * @param card card Object gets deleted
     * @param listId id of that list
     * @return Result Object containing status and an empty payload
     */
    public Result<CardList> removeCardFromList(UUID listId, Card card){
        //TODO make it work by only using the ID
        return this.put("api/list/delete-card/" + listId, card);
    }

    /**
     * Put request to add the card in the request body
     * to the list with the given id
     * @param card card Object needs to be added
     * @param listId id of the list the card gets added to
     * @return Result Object containing status and an empty payload
     */
    public Result<Card> addCardToList(Card card, UUID listId){
        return this.put("api/list/add-card/" + listId, card);
    }

    /**
     * Put request to move the card in the request body
     * from the list with id = id_from
     * to the list with id = id_to
     * @param card card Object gets moved
     * @param cardIdFrom id of origin of card
     * @param cardIdTo id of destination of card
     * @return Result Object containing status and an empty payload
     */
    public Result<CardList> moveCardBetweenLists(Card card, UUID cardIdFrom, UUID cardIdTo){
        return this.put("api/list/move-card/" + cardIdFrom + "/" + cardIdTo, card);
    }

    /**
     * Put request to add the task in the request body
     * to the card with the given id
     * @param task task Object gets added
     * @param cardId id of parent Card
     * @return Result Object containing status and an empty payload
     */
    public Result<Card> addTaskToCard(Task task, UUID cardId){
        return this.put("api/card/add-task/" + cardId, task);
    }

    /**
     * Put request to remove the task in the request body
     * from the card with the given id
     * @param task task Object gets removed
     * @param cardId id of parent Card
     * @return Result Object containing status and an empty payload
     */
    public Result<Card> removeTaskFromCard(Task task, UUID cardId){
        return this.put("api/card/remove-task/" + cardId, task);
    }

    /**
     * Updates the board theme of the board with given id,
     * and saves it in the repository
     */
    public Result<Board> updateBoardTheme(UUID boardId, Theme theme){
        return this.put("api/board/update-theme/" + boardId, theme);
    }

    /**
     * Put request to update a card with the same id as the card in the body
     * @param card the card to update
     * @return Result object containing the success status and the updated card
     */
    public Result<Card> updateCard(Card card) {
        return this.put("api/card/update-card/", card);
    }


    //Websocket related utils

    /** Generic method to register websockets listeners
     * @param dest destination websocket endpoint
     * @param consumer function, gets called from accept()
     */
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
     * Websocket function that sends data through an established websocket
     * @param dest destination websocket endpoint on the server
     * @param payload payload Object
     */
    public void send(String dest, Object payload){
        session.send(dest,payload);
    }



    /**
     * Get request to get the Board from the server repository
     */
    public Result<Board> getBoard(UUID boardID) {

        return this.get("api/board/{id}/" + boardID);
    }

}