package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;
import client.scenes.*;
import com.google.inject.Injector;

import client.scenes.MainCtrl;
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


        //Scenes corresponding to CardList functionalities
        var newList = FXML.load(AddListCtrl.class, "client", "scenes", "AddList.fxml");
        var listOverview = FXML.load(ListOverviewCtrl.class, "client", "scenes", "ListOverview.fxml");

        var draggable = FXML.load(DragController.class, "client", "scenes", "DragTestShowCase.fxml");



        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, newList, listOverview, draggable);



    }
}
