package client.scenes;

import client.dataclass_controllers.ListCtrl;
import com.google.inject.Inject;

public class AddCardCtrl {

    private final SceneCtrl sceneCtrl;
    private final ListCtrl listCtrl;

    private int listID;

    @Inject
    public AddCardCtrl(SceneCtrl sceneCtrl, ListCtrl listCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.listCtrl = listCtrl;
    }


    /** Setter for the list ID
     * @param listID
     */
    public void setCurrentListID(int listID) {
        this.listID = listID;
    }

    /**
     * Creates new card on the server
     * Accessed trough addCard view, must exit to board
     */
    public void createCard(){
        listCtrl.createCard(listID);
        close();
    }


    /**
     * Closes add task window
     */
    public void close(){
        sceneCtrl.showBoard();
    }
}
