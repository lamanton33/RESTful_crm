package client;

import client.components.BoardComponentCtrl;
import client.utils.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultiboardCtrl {

    private MyFXML fxml;
    private List<Pair<BoardComponentCtrl, Parent>>  boardComponentPairs;

    private Pair<BoardComponentCtrl, Parent>  boardComponentPair;

    private ArrayList<UUID> localBoards;

    public MultiboardCtrl() {
    }

    @Inject
    public MultiboardCtrl(MyFXML fxml) {
        this.fxml = fxml;
        this.boardComponentPairs = new ArrayList<>();
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     */
    public Pair<BoardComponentCtrl, Parent> createBoard(){
        this.boardComponentPair = fxml.load(
                BoardComponentCtrl.class, "client", "scenes", "components", "BoardComponent.fxml");
        this.boardComponentPairs.add(boardComponentPair);
        BoardComponentCtrl boardComponentCtrl = boardComponentPair.getKey();
        boardComponentCtrl.initializeBoard();
        return boardComponentPair;
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     *     loads the last board that was saved locally
     */
    public Pair<BoardComponentCtrl, Parent> loadBoard(){
        this.boardComponentPair = fxml.load(
                BoardComponentCtrl.class, "client", "scenes", "components", "BoardComponent.fxml");
        this.boardComponentPairs.add(boardComponentPair);
        BoardComponentCtrl boardComponentCtrl = boardComponentPair.getKey();
        boardComponentCtrl.setBoard(loadUUID());
        return boardComponentPair;
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     *     locads the last board that was saved locally from UUID
     */
    private UUID loadUUID() {
        File file = new File("localBoards.txt");

        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                localBoards = (ArrayList<UUID>) ois.readObject();
                ois.close();
                return localBoards.get(localBoards.size() - 1);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     */
    public Pair<BoardComponentCtrl, Parent> openBoard(UUID boardId){
        Pair<BoardComponentCtrl, Parent> boardPair = fxml.load(BoardComponentCtrl.class, "client", "scenes",
                "components", "BoardComponent.fxml");
        this.boardComponentPair = boardPair;
        this.boardComponentPairs.add(boardPair);
        BoardComponentCtrl boardComponentCtrl = boardPair.getKey();
        boardComponentCtrl.setBoard(boardId);
        return boardPair;
    }

    public void saveBoard(UUID boardId){

        File file = new File("localBoards");

        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                localBoards = (ArrayList<UUID>) ois.readObject();
                localBoards.add(boardId);
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        } else {
            localBoards = new ArrayList<>();
            localBoards.add(boardId);
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(localBoards);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the ArrayList
        System.out.println(localBoards);
    }


    /**
     * loads all boards that were saved locally
     */
    public void loadBoards(){
        ArrayList<UUID> localBoards;

        File file = new File("../local/localBoards.txt");

        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                localBoards = (ArrayList<UUID>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        } else {
            localBoards = new ArrayList<>();
        }

        for (UUID boardId : localBoards) {
            openBoard(boardId);
        }
    }




    /**Getter for the boardComponentCtrl
     * @param boardID
     * @return
     */
    public BoardComponentCtrl getBoardController(UUID boardID) {
        for(Pair<BoardComponentCtrl, Parent> boardPair: boardComponentPairs){
            if(boardPair.getKey().getBoardID().equals(boardID)){
                return boardPair.getKey();
            }
        }
        return null;
    }
}
