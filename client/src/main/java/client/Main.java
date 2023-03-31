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
import client.utils.ConnectionCtrl;
import client.utils.MyFXML;
import client.utils.MyModule;
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
        //Scenes corresponding to AdminLogin functionalities
        var adminLoginFXMLObject = FXML.load(AdminLoginCtrl.class, "client", "scenes", "AdminLogin.fxml");

        //Scenes corresponding to CardList functionalities
        var createNewListFXMLObject = FXML.load(AddListCtrl.class, "client", "scenes", "addList.fxml");
        var addCardFXMLObject = FXML.load(AddCardCtrl.class, "client", "scenes", "addCard.fxml");
        var customizeBoardFXMLObject = FXML.load(CustomizeBoardCtrl.class, "client", "scenes", "CustomizeBoard.fxml");
        var boardsOverviewFXMLObject = FXML.load(BoardOverviewCtrl.class, "client", "scenes", "BoardsOverview.fxml");

        var sceneCtrl = INJECTOR.getInstance(SceneCtrl.class);
        sceneCtrl.initialize(primaryStage,
                connectServerFXMLObject,
                createNewListFXMLObject,
                addCardFXMLObject,
                customizeBoardFXMLObject, boardsOverviewFXMLObject, adminLoginFXMLObject);}}