package client.components;

import client.utils.MyFXML;
import client.SceneCtrl;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.*;

import client.utils.ServerUtils;
import commons.*;
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

@Controller
public class BoardComponentCtrl{
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    @FXML
    private Label boardTitle = new Label();
    @FXML
    private Label boardDescription = new Label();
    @FXML
    private HBox listContainer;

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
            try {
                Result result = (Result) payload;
                JavaType type = objectMapper.getTypeFactory().constructType(Board.class);
                this.board =  objectMapper.convertValue(result.value, type);;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
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
    }

    /**
     * Refreshes the board with up-to-date data, propagates trough ListComponentCtrl
     */
    public void refresh() {
        boardTitle.setText(board.boardTitle);
        boardDescription.setText(board.description);

        List<CardList> cardListLists = board.getCardListList();
        ObservableList<Node> listNodes = listContainer.getChildren();
        // This approach is chosen over listNodes.clear() because it keeps the add list button in place
        listNodes.remove(0,listNodes.size()-1);
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
    public void addCardToList(Card card, int cardListId) {
        for (ListComponentCtrl listComponentCtrl : listComponentCtrls) {
            if (listComponentCtrl.getListId() == cardListId) {
                listComponentCtrl.addSingleCard(card);
            }
        }
    }
}
