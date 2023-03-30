package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import client.components.TaskComponentCtrl;
import client.utils.*;
import commons.*;
import commons.utils.IDGenerator;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddCardCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private final BoardComponentCtrl boardComponentCtrl;
    private final IDGenerator idGenerator;
    private final MultiboardCtrl multiboardCtrl;
    private final List<TaskComponentCtrl> taskComponentCtrls;
    private final MyFXML fxml;

    private CardList cardList;
    private Card card;
    private UUID cardListId;
    private boolean created;

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
                        IDGenerator idGenerator,
                        MultiboardCtrl multiboardCtrl,
                        MyFXML fxml){
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
        this.idGenerator = idGenerator;
        this.multiboardCtrl = multiboardCtrl;
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
        var cardTitle = titleOfCard.getText();
        var description = this.description.getText();
        var tasks = new ArrayList<Task>();
        taskComponentCtrls.forEach(ctrl -> tasks.add(ctrl.getTask()));

        return new Card(idGenerator.generateID(),
                cardList,
                cardTitle,
                description,
                tasks,
                new ArrayList<>());
    }

    /**
     * @return Card
     */
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
        Card newCard = getNewCard();
        System.out.println("Creating a card with id\t" + newCard.cardID + "\tin list\t" + cardList.cardListId);
        try {
            if(!created) {
                var result = server.addCardToList(newCard, cardList.cardListId);
                if (!result.success) {
                    sceneCtrl.showError(result.message, "Failed to create card");
                }
                multiboardCtrl.getBoardController(newCard.cardList.boardId).addCardToList(result.value, cardList.cardListId);
            }else{
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
    public void setCurrentList(CardList cardList) {
        this.cardList = cardList;
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
