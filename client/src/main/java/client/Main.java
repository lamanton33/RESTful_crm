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

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import client.scenes.components.BoardComponentCtrl;
import client.scenes.dataclass_controllers.BoardCtrl;
import client.scenes.dataclass_controllers.ListCtrl;
import client.scenes.MainCtrl;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /** Main method for the client */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /** Initializes JavaFX and starts the main controller */
    @Override
    public void start(Stage primaryStage) throws IOException {

        //scene to connect to a server
        var connectServerFXMLObject = FXML.load(ConnectionCtrl.class, "client", "scenes", "ConnectToServer.fxml");

        //Scene utils
        var draggableFXMLObject = FXML.load(DragController.class, "client", "scenes", "DragTestShowCase.fxml");

        //Scenes corresponding to CardList functionalities
        var createNewListFXMLObject = FXML.load(AddListCtrl.class, "client", "scenes", "addList.fxml");
        var boardOverviewFXMLObject = FXML.load(BoardComponentCtrl.class, "client", "scenes","components", "BoardComponent.fxml");
        var addCardFXMLObject = FXML.load(AddCardCtrl.class, "client", "scenes", "addCard.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage,
                connectServerFXMLObject,
                createNewListFXMLObject,
                boardOverviewFXMLObject,
                draggableFXMLObject,
                addCardFXMLObject);
    }
}