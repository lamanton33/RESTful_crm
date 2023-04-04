package client.components;

import client.*;
import client.interfaces.*;
import client.utils.*;
import com.google.inject.*;
import commons.*;
import commons.utils.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.*;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.*;

import java.io.Closeable;
import java.util.*;

@Controller
public class BoardComponentCtrl implements InstanceableComponent, Closeable {

    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    private List<ListComponentCtrl> listComponentCtrls;
    private IDGenerator idGenerator;
    private ServerUtils server;
    private StompSession.Subscription subscription;

    @FXML
    private Label boardTitle = new Label();
    @FXML
    private Label boardDescription = new Label();
    @FXML
    private HBox listContainer;

    private Board board;



    /** Initialises the controller using dependency injection */
    @Inject
    public BoardComponentCtrl(RandomIDGenerator idGenerator, ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.listComponentCtrls = new ArrayList<>();
        this.idGenerator = idGenerator;
    }

    /**
     * Initializes a new board
     */
    public UUID initializeBoard(String title, String descriptionText){
        this.board = new Board(title, new ArrayList<>(), descriptionText,
                false, null, null);
        this.board.setBoardID(idGenerator.generateID());
        sceneCtrl.setBoardIDForAllComponents(board.getBoardID());
        registerForMessages();
        server.addBoard(this.board);
        System.out.println("Created a new board with id: \t" + this.board.getBoardID());
        return board.getBoardID();
    }

    /** Loads in an existing board
     * @param boardid
     */
    public void setBoard(UUID boardid){
        this.board = server.getBoard(boardid).value;
        sceneCtrl.setBoardIDForAllComponents(boardid);
        System.out.println("Loaded in a board with id " + boardid);
        refresh();
    }

    /**
     * Registers the component for receiving message from the websocket
     */
    public void registerForMessages(){
        unregisterForMessages();
        System.out.println("Board: \t" + board.getBoardID() + " registered for messaging");
        subscription = server.registerForMessages("/topic/update-board/", UUID.class, payload ->{
            System.out.println("Endpoint \"/topic/update-board/\" has been hit by a board with the id:\t"
                    + payload.toString());
            try {
                if(payload.equals(this.board.getBoardID())){
                    System.out.println("Refreshing board:\t" + board.getBoardID() + "\twith\t"
                            + board.cardListList.size() + "\tlists");
                    // Needed to prevent threading issues
                    Platform.runLater(() -> refresh());
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
                }
            }
        );
    }

    @Override
    public void unregisterForMessages() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    /**
     * Refreshes overview with updated data
     */
    public void refresh() {
        close();
        // Make a REST call to get the updated board from the server
        Result<Board> res = server.getBoard(board.getBoardID());
        board = res.value;
        if(res.success){
            // Update the UI with the updated board information
            boardTitle.setText(board.boardTitle);
            boardDescription.setText(board.description);

            // Clear the list container to remove the old lists from the UI
            ObservableList<Node> listNodes = listContainer.getChildren();
            listNodes.remove(0, listNodes.size()-1);

            // Add the updated lists to the UI
            List<CardList> cardListLists = board.getCardListList();
            for (CardList list : cardListLists) {
                addList(list);
            }
        }
        registerForMessages();
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
        cardList.setCardListId(idGenerator.generateID());
        cardList.boardId = board.getBoardID();

        board.cardListList.add(cardList);
        server.addList(cardList);
        System.out.println("Creating a list with id\t " + cardList.cardListId + " on board " + cardList.boardId);
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
        listComponentCtrls.add(listComponentCtrl);

        listNodes.add(listNodes.size()-1, parent);
    }

    /**
     * Adds a card to the list in the UI. It finds the list it's supposed to add it to and then adds it.
     * */
    public void addCardToList(Card card, UUID cardListId) {
        for (ListComponentCtrl listComponentCtrl : listComponentCtrls) {
            if (listComponentCtrl.getListId().equals(cardListId)) {
                listComponentCtrl.addSingleCard(card);
            }
        }
    }

    /**
     * Goes to add new list scene
     */
    public void openAddListScene() {
        sceneCtrl.showAddList();
    }

    /** Getter for the boardId
     * @return UUID of the boardId
     */
    public UUID getBoardID() {
        return board.getBoardID();
    }

    /**
     * @param scene the scene to set
     *              Sets the scene for the board
     */
    public void setScene(Scene scene) {
        sceneCtrl.setBoard(scene);
    }

    /**Goes to the home screen */
    public void backToOverview(ActionEvent actionEvent) {
        close();
        sceneCtrl.showMultiboard();
    }
    @Override
    public void close() {
        unregisterForMessages();
        listComponentCtrls.forEach(ListComponentCtrl::close);
    }
}
