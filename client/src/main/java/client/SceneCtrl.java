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
package client;

import client.scenes.AddCardCtrl;
import client.scenes.AddListCtrl;
import client.scenes.CustomizeBoardCtrl;
import client.utils.ConnectionCtrl;
import client.utils.DragController;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.UUID;

public class SceneCtrl {


    private Stage primaryStage;
    //Scenes
    private Scene connectServerScene;
    private Scene addListScene;
    private Scene draggableScene;
    private Scene addCardScene;
    private Scene customizeBoardScene;

    //Controllers
    private ConnectionCtrl connectServerCtrl;
    private AddListCtrl addListCtrl;
    private DragController draggableCtrl;
    private AddCardCtrl addCardCtrl;
    private CustomizeBoardCtrl customizeBoardCtrl;

    private MultiboardCtrl multiboardCtrl;
    private Scene boardScene;

    @Inject
    public SceneCtrl(MultiboardCtrl multiboardCtrl) {
        this.multiboardCtrl = multiboardCtrl;
    }

    /**
     * Initializes the primary stage, loads in all the scenes and appropriate controllers
     */
    public void initialize(Stage primaryStage       ,
                           Pair<ConnectionCtrl      , Parent> connectServerPair,
                           Pair<AddListCtrl         , Parent> createNewListPair,
                           Pair<DragController      , Parent> draggablePair,
                           Pair<AddCardCtrl         , Parent> addCardPair,
                           Pair<CustomizeBoardCtrl  , Parent> customizeBoardPair
                           ) {
        this.primaryStage = primaryStage;

        this.connectServerScene =   new Scene(connectServerPair.getValue());
        this.addListScene =         new Scene(createNewListPair.getValue());
        this.draggableScene =       new Scene(draggablePair.getValue());
        this.addCardScene =         new Scene(addCardPair.getValue());
        this.customizeBoardScene =  new Scene(customizeBoardPair.getValue());

        this.connectServerCtrl=     connectServerPair.getKey();
        this.addListCtrl =          createNewListPair.getKey();
        this.draggableCtrl=         draggablePair.getKey();
        this.addCardCtrl =          addCardPair.getKey();
        this.customizeBoardCtrl =   customizeBoardPair.getKey();

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
        primaryStage.setScene(addListScene);
    }

    /**
     * Set the primary scene to the List overview scene
     */
    public void showBoard() {
        primaryStage.setTitle("List: Overview");
        primaryStage.setScene(boardScene);

    }

    /** Setter for the boardScene
     * @param boardScene
     */
    public void setBoard(Scene boardScene) {
        this.boardScene = boardScene;
        showBoard();
    }

    /** Shows the connection dialog. This is the first thing the user sees when the application starts up. */
    public void showConnect() {
        primaryStage.setTitle("XLII: Connect to Server");
        primaryStage.setScene(connectServerScene);
    }

    /**
     * Open the card Creation pop-up
     * @param listID id of the list the card will be associated with
     */
    public void showCardCreationPopup(UUID listID){
        primaryStage.setTitle("XLII: Adding card");
        primaryStage.setScene(addCardScene);
        addCardCtrl.setCurrentListID(listID);
    }

    /**
     * In the case of an error this method gives feedback to the client that something has gone wrong.
     * @param message error message
     * @param header the title of the message
     */
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

    public void setBoardIDForAllComponents(UUID boardID){
        addListCtrl.setBoardID(boardID);
    }

    /**
     * Shows the scene to be able to customize the theme of a board
     */
    public void showCustomizeBoard(){
        primaryStage.setTitle("XLII: Customize Board");
        primaryStage.setScene(customizeBoardScene);
    }
}
