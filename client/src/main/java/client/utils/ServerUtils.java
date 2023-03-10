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

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import commons.CardList;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";


    /**
     * Post request to add the CardList list to the server repository
     */
    public CardList addList(CardList list) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/List/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(list, APPLICATION_JSON), CardList.class);
    }

    /**
     * Get request to get all the CardLists from the server repository
     */
    public List<CardList> getLists() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/List/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<CardList>>() {
                });
    }


    /**
     * Delete request to delete the CardList with given id from the server repository
     */
    public CardList deleteList(Integer id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/List/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete(new GenericType<CardList>() {
                });
    }

    /**
     * Put request to update the CardList with given id to the CardList list
     */
    public CardList editList(CardList list, Integer id) {
        System.out.println("Prev " + list.cardListTitle);
        return ClientBuilder.newClient(new ClientConfig())//
                .target(SERVER).path("api/List/" + id)//
                .request(APPLICATION_JSON)//
                .accept(APPLICATION_JSON)//
                .put(Entity.entity(list, APPLICATION_JSON), CardList.class);
    }
}



