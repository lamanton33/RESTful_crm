package client.scenes;

import com.google.inject.Inject;

public class AddListCtrl {
    private int listID;
    //TODO the listID should not be parked here, but rather directly passed trough somehow
    @Inject
    public AddListCtrl() {

    }


    public int getCurrentListID() {
        return listID;
    }

    public void setCurrentListID(int listID) {
        this.listID = listID;
    }
}
