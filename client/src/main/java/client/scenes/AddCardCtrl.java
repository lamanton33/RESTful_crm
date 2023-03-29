package client.scenes;

import client.*;
import client.components.*;
import client.utils.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.inject.*;
import java.util.*;

public class AddCardCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private final BoardComponentCtrl boardComponentCtrl;

    private UUID cardListId;
    private final MyFXML fxml;
    private final List<TaskComponentCtrl> taskComponentCtrls;
    private boolean created;
    private Card card;

    @FXML
    private TextField titleOfCard;
    @FXML
    private TextArea description;
    @FXML
    public VBox taskBox;
    @FXML
    public TextField taskTitle;

    @Inject
    public AddCardCtrl (ServerUtils server,
                        SceneCtrl sceneCtrl,
                        BoardComponentCtrl boardComponentCtrl,
                        MyFXML fxml){
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
        this.fxml = fxml;
        this.created = false;
        this.taskComponentCtrls = new ArrayList<>();
    }

    /**
     * Closes add task window
     */
    public void close(){
        sceneCtrl.showBoard();
        clearFields();
    }

    /**
     * Gets the info of the card
     * @return instance of new Card with picked title and description
     */
    public Card getNewCard(){
        var titleVar = titleOfCard.getText();
        var description = this.description.getText();
        var tasks = new ArrayList<Task>();
        taskComponentCtrls.forEach(ctrl -> tasks.add(ctrl.getTask()));

        return new Card(titleVar, description, tasks);
    }

    private Card getExistingCard() {
        var titleVar = titleOfCard.getText();
        var description = this.description.getText();
        var tasks = new ArrayList<Task>();
        taskComponentCtrls.forEach(ctrl -> tasks.add(ctrl.getTask()));

        card.cardTitle = titleVar;
        card.cardDescription = description;
        card.taskList = tasks;
        return card;
    }

    /**
     * Creates new card on the server
     */
    public void createCard(){
        System.out.println("Creating card");
        try {
            if(!created) {
                var result = server.addCardToList(getNewCard(), cardListId);
                if (!result.success) {
                    sceneCtrl.showError(result.message, "Failed to create card");
                    return;
                }
                boardComponentCtrl.addCardToList(result.value, cardListId);
            } else {
                var result = server.updateCard(getExistingCard());
                if(!result.success) {
                    sceneCtrl.showError(result.message, "Failed to save card");
                    return;
                }
                boardComponentCtrl.refresh();
            }
        } catch (WebApplicationException e) {
            sceneCtrl.showError(e.getMessage(), "Failed to save card");
            return;
        }

        clearFields();
        sceneCtrl.showBoard();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        titleOfCard.clear();
        description.clear();
        taskBox.getChildren().removeAll(taskBox.getChildren());
        taskTitle.clear();
        created = false;
        card = null;
    }

    /** Sets the id of the list to add the card to */
    public void setCurrentListID(UUID cardListID) {
        this.cardListId = cardListID;
    }

    /** Instantiates the view to the card to be edited by setting the ui elements to the specified data.
     * Sets a flag so it's known this is a card to be edited, not created. */
    public void edit(Card card) {
        created = true;
        this.card = card;

        titleOfCard.setText(card.cardTitle);
        description.setText(card.cardDescription);
        for(var task : card.taskList) {
            addTaskToUI(task.taskTitle, task.isCompleted);
        }
    }

    private void addTaskToUI(String title, boolean completed) {
        var taskPair = fxml.load(TaskComponentCtrl.class, "client", "scenes", "components", "TaskComponent.fxml");
        taskBox.getChildren().add(taskPair.getValue());
        var ctrl = taskPair.getKey();

        taskComponentCtrls.add(ctrl);
        ctrl.setTask(title, completed);
    }

    /** Adds a task from the title set in the text box above. */
    public void addTask() {
        var title = taskTitle.getText();
        if(title.isBlank()) {
            return;
        }
        taskTitle.clear();
        addTaskToUI(title, false);
    }

    /** Deletes the task this component controls */
    public void deleteTask(TaskComponentCtrl taskComponentCtrl) {
        var index = taskComponentCtrls.indexOf(taskComponentCtrl);
        taskComponentCtrls.remove(index);
        taskBox.getChildren().remove(index);
    }
}
