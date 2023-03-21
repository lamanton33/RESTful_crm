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
package client.scenes;

import client.scenes.dataclass_controllers.BoardCtrl;
import client.scenes.dataclass_controllers.CardCtrl;
import client.scenes.dataclass_controllers.ListCtrl;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.util.*;

public class MainCtrl {

    private Stage primaryStage;

    //Scenes
    private Scene connectServerScene;
    private Scene createNewListScene;
    private Scene boardOverviewScene;
    private Scene draggableScene;
    private Scene addCardScene;
    private Scene addListScene;

    //Controllers
    private ConnectionCtrl connectServerCtrl;
    private BoardCtrl boardCtrl;
    private DragController dragController;
    private ConnectionCtrl connectCtrl;
    private CardCtrl cardCtrl;
    private Object createNewListCtrl;
    private Object draggableCtrl;
    private AddCardCtrl addCardCtrl;
    private AddListCtrl addListCtrl;


    /**
     * Initializes the primary stage
     */
    public void initialize(Stage primaryStage   ,
                           Pair<ConnectionCtrl  , Parent> connectServerPair,
                           Pair<ListCtrl        , Parent> createNewListPair,
                           Pair<BoardCtrl       , Parent> boardOverviewPair,
                           Pair<DragController  , Parent> draggablePair,
                           Pair<AddCardCtrl, Parent> addCardPair)
        {
        this.primaryStage = primaryStage;

        this.connectServerScene =   new Scene(connectServerPair.getValue());
        this.createNewListScene =   new Scene(createNewListPair.getValue());
        this.boardOverviewScene =   new Scene(boardOverviewPair.getValue());
        this.draggableScene =       new Scene(draggablePair.getValue());
        this.addCardScene =        new Scene(addCardPair.getValue());
        //this.addListScene =        new Scene(addListPair.getValue());

        this.connectServerCtrl=     connectServerPair.getKey();
        this.createNewListCtrl =    createNewListPair.getKey();
        this.boardCtrl =            boardOverviewPair.getKey();
        this.draggableCtrl=         draggablePair.getKey();
        this.addCardCtrl =          addCardPair.getKey();
        //this.addListCtrl =          addListPair.getKey();


        //when starting up connect to the server
        //should be replaced by a homescreen at some point
        showConnect();
        primaryStage.show();
    }

    /**
     * Set the primary scene to the Create New List scene
     */
    public void showAddList() {
        primaryStage.setTitle("List: Create List");
        primaryStage.setScene(createNewListScene);
    }

//    /** Adds a list to the current board UI to be able to see it */
//    public void addListToBoard(CardList list) {
//        boardCtrl.addSingleList(list);
//    }

    /** Refreshes the board. This will get all the lists from the server and then recreate the board UI */
    public void refreshBoard() {
        boardCtrl.refresh();
    }

    /**
     * Set the primary scene to the List overview scene
     */
    public void showBoard() {
        primaryStage.setTitle("List: Overview");
        primaryStage.setScene(boardOverviewScene);
    }

    /** Shows the connection dialog. This is the first thing the user sees when the application starts up. */
    public void showConnect() {
        primaryStage.setTitle("XLII: Connect to Server");
        primaryStage.setScene(connectServerScene);
    }

    /**
     * Sets the primare scene to addCard
     */
    public void showCardCreationPopup(int listID){
        primaryStage.setTitle("XLII: Adding card");
        addListCtrl.setCurrentListID(listID);
        primaryStage.setScene(addCardScene);
    }

    /** In the case of an error this method gives feedback to the client that something has gone wrong. */
    public void showError(String message, String header) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText(message);
        alert.setHeaderText(header);
        var dialog = alert.getDialogPane();
        dialog.getStyleClass().add("root");
        dialog.getStylesheets().add("client/scenes/style.css");
        alert.showAndWait();
    }

    /**
     * Opens test scene for a draggable node
     */
    public void draggableTest(){
        primaryStage.setScene(draggableScene);
        primaryStage.setTitle("hello");
    }

    public BoardCtrl getBoardCtrl() {
        return this.boardCtrl;
    }
}
