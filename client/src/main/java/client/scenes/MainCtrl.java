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

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.util.*;

public class MainCtrl {

    private Stage primaryStage;


    private Scene createNewList;
    private AddListCtrl createListCtrl;

    private Scene listOverview;
    private ListOverviewCtrl listOverviewCtrl;

    private Scene draggable;
    private DragController dragController;

    private Scene editList;

    private ConnectToServerCtrl connectCtrl;
    private Scene connect;

    /**
     * Initializes the primary stage
     */
    public void initialize(Stage primaryStage,
                           Pair<ConnectToServerCtrl, Parent> connect,
                           Pair<AddListCtrl, Parent> newList,
                           Pair<ListOverviewCtrl, Parent> listView,
                           Pair<DragController,Parent> draggable) {
        this.primaryStage = primaryStage;

        this.dragController = draggable.getKey();
        this.draggable = new Scene(draggable.getValue());

        this.connectCtrl = connect.getKey();
        this.connect = new Scene(connect.getValue());

        this.createListCtrl = newList.getKey();
        this.createNewList = new Scene(newList.getValue());

        this.listOverviewCtrl = listView.getKey();
        this.listOverview = new Scene(listView.getValue());

        //when starting up connect to the server
        //should be replace by a homescreen at some point
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

    /**
     * Set the primary scene to the List overview scene
     */
    public void showListOverview() {
        primaryStage.setTitle("List: Overview");
        primaryStage.setScene(listOverview);
        listOverviewCtrl.refresh();
    }

    /** Shows the connection dialog. This is the first thing the user sees when the application starts up. */
    public void showConnect() {
        primaryStage.setTitle("XLII: Connect to Server");
        primaryStage.setScene(connect);
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
}
