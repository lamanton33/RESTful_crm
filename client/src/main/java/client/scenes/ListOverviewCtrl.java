package client.scenes;

import client.scenes.components.ListComponentCtrl;
import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.CardList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;

public class ListOverviewCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<ListComponentCtrl> data;

    @FXML
    private HBox hBoxContainer;

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Goes to add new list scene
     */
    public void addList() {

        mainCtrl.showAddList();
    }

    /**
     * Refreshes overview with updated data
     */
    public void refresh() {
        var lists = server.getLists();
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

}