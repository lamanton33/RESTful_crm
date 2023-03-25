package client.components;

import client.utils.MyFXML;
import client.SceneCtrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.*;

import client.utils.ServerUtils;
import commons.*;
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

@Controller
public class BoardComponentCtrl{
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;

    @FXML
    private Label boardTitle = new Label();
    @FXML
    private Label boardDescription = new Label();
    @FXML
    private HBox hBoxContainer;

    private final ServerUtils server;
    private Board board;

    private List<ListComponentCtrl> listComponentCtrls;


    /** Initialises the controller using dependency injection */
    @Inject
    public BoardComponentCtrl(ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.listComponentCtrls = new ArrayList<>();
    }


    /**
     * Registers the component for receiving message from the websocket
     */
    public void registerForMessages(){
        ObjectMapper objectMapper = new ObjectMapper();
        //websockets init
        //TODO board ID
        server.registerForMessages("/topic/boards", payload ->{
            Result result = (Result) payload;
            JavaType type = objectMapper.getTypeFactory().constructType(Board.class);
            System.out.println("Received Result from server + " + result.success + " of type " + result.value.getClass());
            try {
                Board potentialBoard = objectMapper.convertValue(result.value, type);
                this.board = potentialBoard;

            } catch (RuntimeException e) {
                System.out.println("Failed to parse");
                throw new RuntimeException(e);
            }
            System.out.println(board.toString());
            refresh();
        });
        server.send("/app/boards/get-dummy-board/",null);


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
        CardList cardList = new CardList(listTitle, new ArrayList<>());
        server.addList(cardList);
        refresh();
    }


    /**
     * Refreshes the board with up-to-date data, propagates trough ListComponentCtrl
     */
    public void refresh() {
        System.out.println("Refreshing board with ID " + board.getBoardID());
        boardTitle.setText(board.boardTitle);
        boardDescription.setText(board.description);

        List<CardList> cardListLists = board.getCardListList();
        ObservableList<Node> listNodes = this.hBoxContainer.getChildren();
        listNodes.remove(0, listNodes.size()-1);

        System.out.println("List board " + cardListLists.size() + " lists");
        for (CardList list : cardListLists) {
            addList(list);
        }
    }

    /** Adds a single list to the end of board
     * @param list the list that gets added to the board
     * */
    public void addList(CardList list) {
        ObservableList<Node> listNodes = hBoxContainer.getChildren();
        Pair<ListComponentCtrl, Parent> component = fxml.load(
                ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
        Parent parent = component.getValue();
        ListComponentCtrl listComponentCtrl = component.getKey();
        listComponentCtrl.setList(list);
        listComponentCtrls.add(listComponentCtrl);

        listNodes.add(listNodes.size()-1, parent);
    }

    /**
     * Goes to add new list scene
     */
    public void openAddListScene() {
        sceneCtrl.showAddList();
    }

    /** Adds a card to the list in the UI. It finds the list it's supposed to add it to and then adds it. */
    public void addCardToList(Card card, int cardListId) {
        for (ListComponentCtrl listComponentCtrl : listComponentCtrls) {
            if (listComponentCtrl.getListId() == cardListId) {
                listComponentCtrl.addSingleCard(card);
            }
        }
    }

}
