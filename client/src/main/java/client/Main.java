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

import client.scenes.*;
import client.utils.*;
import com.google.inject.*;
import javafx.application.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

import static com.google.inject.Guice.*;

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

        //Scene utils
        //Scenes corresponding to AdminLogin functionalities
        var adminLoginFXMLObject = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");

        //Scenes corresponding to CardList functionalities
        var createNewListFXMLObject = FXML.load(AddListCtrl.class, "client", "scenes", "addList.fxml");
        var addCardFXMLObject = FXML.load(AddCardCtrl.class, "client", "scenes", "addCard.fxml");
        var addBoardFXMLObject = FXML.load(AddBoardCtrl.class, "client", "scenes", "addBoard.fxml");
        var customizeBoardFXMLObject = FXML.load(CustomizeBoardCtrl.class, "client", "scenes", "CustomizeBoard.fxml");
        var boardsOverviewFXMLObject = FXML.load(BoardsOverviewCtrl.class, "client", "scenes", "BoardsOverview.fxml");
        var joinViaLinkFXMLObject = FXML.load(JoinViaLinkCtrl.class, "client", "scenes", "joinViaLink.fxml");
        var customizeTagFXMLObject =  FXML.load(CustomizeTagCtrl.class, "client", "scenes", "CustomizeTag.fxml");

        var sceneCtrl = INJECTOR.getInstance(SceneCtrl.class);
        sceneCtrl.initialize(primaryStage,
                createNewListFXMLObject,
                addCardFXMLObject,
                customizeBoardFXMLObject,
                boardsOverviewFXMLObject,
                adminLoginFXMLObject,
                addBoardFXMLObject,
                joinViaLinkFXMLObject,
                customizeTagFXMLObject);
    }
}