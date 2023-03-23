package client.dataclass_controllers;

import client.scenes.SceneCtrl;
import client.components.ListComponentCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.CardList;
import commons.Result;
import jakarta.ws.rs.WebApplicationException;
public class ListCtrl {

    private final SceneCtrl sceneCtrl;
    private final ServerUtils server;
    private final Datasource datasource;
    private final ListComponentCtrl listComponentCtrl;

    /** Initialises the controller using dependency injection */
    @Inject
    public ListCtrl(ServerUtils server, SceneCtrl sceneCtrl, Datasource datasource, ListComponentCtrl listComponentCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.datasource = datasource;
        this.listComponentCtrl = listComponentCtrl;
    }

    /** Finds correct cardlist by id and adds card, then asks te board to refresh
     * @param card card to be added
     * @param listId id of list cards gets added to
     * */
    public void addCardToList(Card card, int listId) {
        for(CardList cardList: datasource.getBoard().getCardListList()){
            if(cardList.getListID() == listId){
                cardList.addCard(card);
                listComponentCtrl.refresh();
            }
        }
    }

    /**
     * Creates new card empty on the server
     * @param listId id of the list the card gets added to
     */
    public void createCard(int listId){
        Card newCard = new Card("");
        try {
            Result<Card> result = server.addCardToList(newCard, listId);
            if (!result.success) {
                sceneCtrl.showError(result.message, "Failed to create card");
            }
            addCardToList(newCard, listId);
        } catch (WebApplicationException e) {
            sceneCtrl.showError(e.getMessage(), "Failed to create card");
        }
    }
}