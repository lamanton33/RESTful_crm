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

import java.util.*;

import static jakarta.ws.rs.core.MediaType.*;

public class ServerUtils {

    private String server;

    /** Sets the server connection string. */
    public void setServer(String server) {
        this.server = server;
    }

    /** Tries to "connect" to a server by trying to see if the server exists. */
    public Result<Object> connect() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/api/status")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Post request to add the CardList list to the server repository
     */
    public Result<CardList> addList(CardList list) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(server).path("api/List/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(list, APPLICATION_JSON), new GenericType<>() {});
    }



    /**
     * Get request to get all the CardLists from the server repository
     */
    public Result<List<CardList>> getLists() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(server).path("api/List/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }


    /**
     * Delete request to delete the CardList with given id from the server repository
     */
    public Result<CardList> deleteList(Integer id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(server).path("api/List/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<>() {
                });
    }

    /**
     * Put request to update the CardList with given id to the CardList list
     */
    public Result<CardList> editList(CardList list, Integer id) {
        return ClientBuilder.newClient(new ClientConfig())//
                .target(server).path("api/List/" + id)//
                .request(APPLICATION_JSON)//
                .accept(APPLICATION_JSON)//
                .put(Entity.entity(list, APPLICATION_JSON), new GenericType<>() {});
    }

    /**
     * Put request to remove the card in the request body
     * from the list with the given id
     */
    public Result<CardList> removeCardToList(Card card, Integer id){
        return ClientBuilder.newClient(new ClientConfig())//
                .target(server).path("api/List/" + id + "/deleteCard/")//
                .request(APPLICATION_JSON)//
                .accept(APPLICATION_JSON)//
                .put(Entity.entity(card, APPLICATION_JSON), new GenericType<>() {});
    }

    /**
     * Put request to add the card in the request body
     * to the list with the given id
     */
    public Result<CardList> addCardToList(Card card, Integer id){
        return ClientBuilder.newClient(new ClientConfig())//
                .target(server).path("api/List/" + id + "/addCard/")//
                .request(APPLICATION_JSON)//
                .accept(APPLICATION_JSON)//
                .put(Entity.entity(card, APPLICATION_JSON), new GenericType<>() {});
    }

    /**
     * Put request to move the card in the request body
     * from the list with id = id_from
     * to the list with id = id_to
     */
    public Result<CardList> moveCardBetweenLists(Card card, Integer id_from, Integer id_to){
        return ClientBuilder.newClient(new ClientConfig())//
                .target(server).path("api/List/" + id_from + "/" + id_to + "/moveCard/")//
                .request(APPLICATION_JSON)//
                .accept(APPLICATION_JSON)//
                .put(Entity.entity(card, APPLICATION_JSON), new GenericType<>() {});
    }

    /**
     * Post request to add the Card to the server repository
     */
    public Result<Card> addCard(Card card) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(server).path("api/Card/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(card, APPLICATION_JSON), new GenericType<>() {});
    }
}