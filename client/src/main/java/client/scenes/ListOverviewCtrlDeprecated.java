//package client.scenes;
//
//import client.*;
//import client.scenes.components.ListComponentCtrl;
//import client.utils.MyFXML;
//import com.google.inject.*;
//
//import client.utils.ServerUtils;
//import commons.*;
//import javafx.collections.*;
//import javafx.fxml.FXML;
//import javafx.scene.*;
//import javafx.scene.control.TableColumn;
//import javafx.scene.layout.HBox;
//
//import java.util.*;
//
//public class ListOverviewCtrl {
//
//    private final ServerUtils server;
//    private final MainCtrl mainCtrl;
//    private final MyFXML fxml;
//    private final List<ListComponentCtrl> listControllers;
//
//    @FXML
//    private HBox hBoxContainer;
//
//    @Inject
//    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl, MyFXML fxml) {
//        this.server = server;
//        this.mainCtrl = mainCtrl;
//        this.fxml = fxml;
//        listControllers = new ArrayList<>();
//    }
//
//    /**
//     * Goes to add new list scene
//     */
//    public void addList() {
//        mainCtrl.showAddList();
//    }
//
//    /**
//     * Refreshes overview with updated data
//     */
//    public void refresh() {
//        var cardLists = server.getLists().value;
//        var listNodes = hBoxContainer.getChildren();
//        listNodes.remove(0, listNodes.size()-1);
//
//        for (var list : cardLists) {
//            addSingleList(list);
//        }
//    }
//
//
//    /**
//     * Deletes list from the overview
//     */
//    public void delete() {
////        Integer deletingIdx = table.getSelectionModel().getSelectedItem().cardListID;
////        if (deletingIdx != null) {
////            server.deleteList(deletingIdx);
////            table.getItems().remove(deletingIdx);
////            refresh();
////        }
//    }
//
//    /**
//     * Edits the name on the list by clicking on it
//     */
//    public void editChange(TableColumn.CellEditEvent<CardList, String> cardListStringCellEditEvent) {
////        CardList list = table.getSelectionModel().getSelectedItem();
////        list.setCardListTitle(cardListStringCellEditEvent.getNewValue());
////        server.editList(list, list.cardListID);
//    }
//
//    /** Adds a single list to the board */
//    public void addSingleList(CardList list) {
//        if (list == null) {
//            return;
//        }
//
//        var listNodes = hBoxContainer.getChildren();
//        var component = fxml.load(ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
//        var parent = component.getValue();
//        var ctrl = component.getKey();
//        ctrl.setList(list);
//        listControllers.add(ctrl);
//        listNodes.add(listNodes.size()-1, parent);
//    }
//
//    /** Adds a card to the list in the UI. It finds the list it's supposed to add it to and then adds it. */
//    public void addCardToList(Card card, int cardListId) {
//        for (var listCtrl : listControllers) {
//            if (listCtrl.getListId() == cardListId) {
//                listCtrl.addSingleCard(card);
//            }
//        }
//    }
//
//}