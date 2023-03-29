package client.components;

import client.SceneCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import commons.utils.IDGenerator;
import commons.utils.RandomIDGenerator;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.CardList;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BoardComponentCtrl implements InstanceableComponent {

    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    @FXML
    private Label boardTitle = new Label();
    @FXML
    private Label boardDescription = new Label();
    @FXML
    private HBox listContainer;

    private IDGenerator idGenerator;
    private ServerUtils server;
    private Board board;
    private List<ListComponentCtrl> listComponentCtrls;


    /** Initialises the controller using dependency injection */
    @Inject
    public BoardComponentCtrl(RandomIDGenerator idGenerator, ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.listComponentCtrls = new ArrayList<>();
        this.idGenerator = idGenerator;
    }
    
    public void initializeBoard(){
        this.board = new Board("New title", new ArrayList<CardList>(), "Add an description",
                false, null, null);
        this.board.setBoardID(idGenerator.generateID());
        sceneCtrl.setBoardIDForAllComponents(board.getBoardID());
        System.out.println("Created a new board with id: " + this.board.getBoardID());
        registerForMessages();
        server.addBoard(this.board);
    }

    public void setBoard(UUID boardid){
        this.board = server.getBoard(boardid).value;
        System.out.println("Loaded in a board with id " + this.board.getBoardID());
        registerForMessages();
    }

    /**
     * Registers the component for receiving message from the websocket
     */
    public void registerForMessages(){
        System.out.println("Board: " + board.getBoardID() + " registered for messaging");
        server.registerForMessages("/topic/update-board/", UUID.class, payload ->{
            System.out.println("Endpoint \"/topic/update-board/\" has been hit by a board with the id:" + payload.toString());
            try {
                if(payload.equals(this.board.getBoardID())){
                    System.out.println("Board: " + board.getBoardID() + " is refreshing");
                    // Needed to prevent threading issues
                    Platform.runLater(() -> refresh());
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
                }
            }
        );
    }

    /**
     * Deletes list from the overview
     */
    public void delete() {
//        Integer deletingIdx = table.getSelectionModel().getSelectedItem().cardListID;
//        if (deletingIdx != null) {
//            server.deleteList(deletingIdx);
//            table.getItems().remove(deletingIdx);
//            refresh();
//        }
    }

    /**
     * Edits the name on the list by clicking on it
     */
    public void editChange(TableColumn.CellEditEvent<CardList, String> cardListStringCellEditEvent) {
//        CardList list = table.getSelectionModel().getSelectedItem();
//        list.setCardListTitle(cardListStringCellEditEvent.getNewValue());
//        server.editList(list, list.cardListID);
    }

    /**
     * Creates a new list and shows the updated list overview, takes in a title
     * @param listTitle
     */
    public void createList(String listTitle){
        CardList cardList = new CardList(listTitle, new ArrayList<>(), board);
        cardList.setCardListID(idGenerator.generateID());
        server.addList(cardList);
    }

    public UUID getBoardID(){
        return board.getBoardID();
    }


    /**
     * Refreshes overview with updated data
     */

    public void refresh() {
        System.out.println("Refreshing " + board.getBoardID());
        // Make a REST call to get the updated board from the server
        board = server.getBoard(board.getBoardID()).value;

        // Update the UI with the updated board information
        boardTitle.setText(board.boardTitle);
        boardDescription.setText(board.description);

        // Clear the list container to remove the old lists from the UI
        ObservableList<Node> listNodes = listContainer.getChildren();
        listNodes.clear();

        // Add the updated lists to the UI
        List<CardList> cardListLists = board.getCardListList();
        for (CardList list : cardListLists) {
            addList(list);
        }
    }


    /** Adds a single list to the end of board
     * @param list the list that gets added to the board
     * */
    public void addList(CardList list) {
        ObservableList<Node> listNodes = listContainer.getChildren();
        Pair<ListComponentCtrl, Parent> component = fxml.load(
                ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
        Parent parent = component.getValue();
        ListComponentCtrl listComponentCtrl = component.getKey();
        listComponentCtrl.setList(list);
        listComponentCtrls.add( listNodes.size()-1,listComponentCtrl);

        listNodes.add(listNodes.size()-1, parent);
    }
    /**
     * Goes to add new list scene
     */
    public void openAddListScene() {
        sceneCtrl.showAddList();
    }

    /** Adds a card to the list in the UI. It finds the list it's supposed to add it to and then adds it. */
    public void addCardToList(Card card, UUID cardListId) {
        for (ListComponentCtrl listComponentCtrl : listComponentCtrls) {
            if (listComponentCtrl.getListId() == cardListId) {
                listComponentCtrl.addSingleCard(card);
            }
        }
    }

}
