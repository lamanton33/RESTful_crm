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
    private Scene createNewList;
    private Scene listOverview;
    private Scene editList;
    private Scene connect;
    private Scene draggable;
    private Scene CRUDCardCtrl;
    private Scene CRUDCard;

    //Controllers
    private ListCtrl createListCtrl;

    private BoardCtrl boardCtrl;
    private DragController dragController;

    private ConnectionCtrl connectCtrl;


    private CardCtrl cardCtrl;





    /**
     * Initializes the primary stage
     */
    public void initialize(Stage primaryStage,
                           Pair<ConnectionCtrl, Parent> connect,
                           Pair<ListCtrl, Parent> newList,
                           Pair<BoardCtrl, Parent> listView,
                           Pair<DragController,Parent> draggable,
                           Pair<CardCtrl, Parent> addCard),
                            Pair<CRUDCardCtrl, Parent> CRUDCardCtrl) {
        this.primaryStage = primaryStage;

        this.dragController = draggable.getKey();
        this.draggable = new Scene(draggable.getValue());

        this.connectCtrl = connect.getKey();
        this.connect = new Scene(connect.getValue());

        this.createListCtrl = newList.getKey();
        this.createNewList = new Scene(newList.getValue());

        this.boardCtrl = listView.getKey();
        this.listOverview = new Scene(listView.getValue());

        this.cardCtrl = CRUDCardCtrl.getKey();
        this.CRUDCardCtrl = new Scene(CRUDCardCtrl.getValue());

        this.CRUDCardCtrl = addCard.getKey();
        this.addCard = new Scene(addCard.getValue());
        boardCtrl.registerForMessages();


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
        primaryStage.setScene(createNewList);
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
        primaryStage.setScene(listOverview);
    }

    /** Shows the connection dialog. This is the first thing the user sees when the application starts up. */
    public void showConnect() {
        primaryStage.setTitle("XLII: Connect to Server");
        primaryStage.setScene(connect);
    }

    /**
     * Sets the primare scene to addCard
     */
    public void showCardCreationPopup(int listID){
        primaryStage.setTitle("XLII: Adding card");
        CRUDListCtrl.setListID(list);
        primaryStage.setScene(addCard);
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
        primaryStage.setScene(this.draggable);
        primaryStage.setTitle("hello");
    }

    public BoardCtrl getBoardCtrl() {
        return this.boardCtrl;
    }
}
