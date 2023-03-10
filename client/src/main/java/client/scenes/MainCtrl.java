package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.stage.*;


public class MainCtrl {

    private Stage primaryStage;


    private Scene createNewList;
    private AddListCtrl createListCtrl;

    private Scene listOverview;
    private ListOverviewCtrl listOverviewCtrl;

    private Scene editList;


    /**
     * Initializes the primary stage
     */
    public void initialize(Stage primaryStage, Pair<AddListCtrl, Parent> newList,
                           Pair<ListOverviewCtrl, Parent> listView) {
        this.primaryStage = primaryStage;


        this.createListCtrl = newList.getKey();
        this.createNewList = new Scene(newList.getValue());

        this.listOverviewCtrl = listView.getKey();
        this.listOverview = new Scene(listView.getValue());

        //Currently initialized to list overview,
        // should point to home page in end-product
        showListOverview();
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

}
