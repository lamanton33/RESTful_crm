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

import client.components.*;
import client.scenes.*;
import client.utils.*;
import com.google.inject.Inject;
import commons.*;
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
    private Scene addCardScene;
    private Scene customizeBoardScene;
    private Scene adminLoginScene;

    //Controllers
    private ConnectionCtrl connectServerCtrl;
    private AddListCtrl addListCtrl;
    private AddCardCtrl addCardCtrl;
    private CustomizeBoardCtrl customizeBoardCtrl;

    private MultiboardCtrl multiboardCtrl;
    private Scene boardScene;
    private AdminLoginCtrl adminLoginCtrl;

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
                           Pair<AddCardCtrl         , Parent> addCardPair,
                           Pair<CustomizeBoardCtrl  , Parent> customizeBoardPair,
                           Pair<AdminLoginCtrl      , Parent> adminLoginPair
                           ) {
        this.primaryStage = primaryStage;

        this.connectServerScene =   new Scene(connectServerPair.getValue());
        this.addListScene =         new Scene(createNewListPair.getValue());
        this.addCardScene =         new Scene(addCardPair.getValue());
        this.customizeBoardScene =  new Scene(customizeBoardPair.getValue());
        this.adminLoginScene =      new Scene(adminLoginPair.getValue());



        this.connectServerCtrl=     connectServerPair.getKey();
        this.addListCtrl =          createNewListPair.getKey();
        this.addCardCtrl =          addCardPair.getKey();
        this.customizeBoardCtrl =   customizeBoardPair.getKey();
        this.adminLoginCtrl =       adminLoginPair.getKey();

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
     * @param list id of the list the card will be associated with
     */
    public void showCardCreationPopup(CardList list){
        primaryStage.setTitle("XLII: Adding card");
        primaryStage.setScene(addCardScene);
        addCardCtrl.setCurrentList(list);
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


    /** Sets the boardID for all components that need it
     * @param boardID
     */
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

    /** Sets the stage to the card editing scene to start editing the card in the parameters */
    public void editCard(Card card) {
        primaryStage.setTitle("XLII: Adding card");
        primaryStage.setScene(addCardScene);
        addCardCtrl.edit(card);
    }

    /** Deletes the task connected to the controller. */
    public void deleteTask(TaskComponentCtrl taskComponentCtrl) {
        addCardCtrl.deleteTask(taskComponentCtrl);
    }

    /**
     * Sets scene to admin login form
     */
    public void showAdminLoginPopup() {
        primaryStage.setTitle("XLII: Admin login");
        primaryStage.setScene(adminLoginScene);
    }
}


