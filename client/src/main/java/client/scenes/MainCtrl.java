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

    private Scene draggable;
    private DragController dragController;

    private Scene editList;


    /**
     * Initializes the primary stage
     */
    public void initialize(Stage primaryStage, Pair<AddListCtrl, Parent> newList,
                           Pair<ListOverviewCtrl, Parent> listView, Pair<DragController,Parent> draggable) {
        this.primaryStage = primaryStage;

        this.dragController = draggable.getKey();
        this.draggable = new Scene(draggable.getValue());


        this.createListCtrl = newList.getKey();
        this.createNewList = new Scene(newList.getValue());

        this.listOverviewCtrl = listView.getKey();
        this.listOverview = new Scene(listView.getValue());

        draggableTest();
        //Currently initialized to list overview,
        // should point to home page in end-product
//        showListOverview();
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

    /**
     * Opens test scene for a draggable node
     */
    public void draggableTest(){
        primaryStage.setScene(this.draggable);
        primaryStage.setTitle("hello");
    }
}
