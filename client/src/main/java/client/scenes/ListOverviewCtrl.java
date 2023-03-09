package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.CardList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class ListOverviewCtrl implements Initializable{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<CardList> data;

    @FXML
    private TableView<CardList> table;

    @FXML
    private TableColumn<CardList, String> titleCol;

    @FXML
    private TableColumn<CardList, String> idCol;

    @Inject
    public ListOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleCol.setCellValueFactory(l -> new SimpleStringProperty(l.getValue().cardListTitle));
        idCol.setCellValueFactory(l -> new SimpleStringProperty(String.valueOf(l.getValue().cardListID)));

        table.setEditable(true);
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());

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
        data = FXCollections.observableList(lists);
        table.setItems(data);
    }

    /**
     * Deletes list from the overview
     */
    public void delete() {
        Integer deletingIdx = table.getSelectionModel().getSelectedItem().cardListID;
        if (deletingIdx != null) {
            server.deleteList(deletingIdx);
            table.getItems().remove(deletingIdx);
            refresh();
        }
    }

    /**
     * Edits the name on the list by editing on the table cell
     */
    public void editChange(TableColumn.CellEditEvent<CardList, String> cardListStringCellEditEvent) {
        CardList list = table.getSelectionModel().getSelectedItem();
        list.setCardListTitle(cardListStringCellEditEvent.getNewValue());
        server.editList(list, list.cardListID);
        //refresh();
    }

}
