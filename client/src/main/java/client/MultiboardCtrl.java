package client;

import client.components.BoardComponentCtrl;
import client.utils.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultiboardCtrl {

    private MyFXML fxml;
    private List<Pair<BoardComponentCtrl, Parent>>  boardComponentPairs;

    private Pair<BoardComponentCtrl, Parent>  boardComponentPair;

    private List<UUID> localBoards;


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
        UUID boardID = boardComponentCtrl.initializeBoard();
        saveBoard(boardID);
        return boardComponentPair;
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     *     loads the last board that was saved locally
     */
    public Pair<BoardComponentCtrl, Parent> loadBoard(){

        File f = new File("localBoards");

        if(f.exists() && !f.isDirectory()) {
            this.boardComponentPair = fxml.load(
                    BoardComponentCtrl.class, "client", "scenes", "components", "BoardComponent.fxml");
            this.boardComponentPairs.add(boardComponentPair);
            BoardComponentCtrl boardComponentCtrl = boardComponentPair.getKey();
            boardComponentCtrl.setBoard(loadUUID());
            return boardComponentPair;
        }
        else {
            return createBoard();
        }
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     *     locads the last board that was saved locally from UUID
     */
    private UUID loadUUID() {
        File file = new File("localBoards");

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
        boardComponentCtrl.setScene(new Scene(boardPair.getValue()));

        return boardPair;
    }

    /**
     * Saves board
     * @param boardId
     */
    public void saveBoard(UUID boardId){

        File file = new File("localBoards");

        try {
            if (file.exists()) {
                localBoards = loadBoards();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                localBoards.add(boardId);
                oos.writeObject(localBoards);
                oos.close();
            } else {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                localBoards = new ArrayList<UUID>();
                localBoards.add(boardId);
                oos.writeObject(localBoards);
                oos.close();
            }
            System.out.println(localBoards);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * loads all boards that were saved locally
     */
    public List<UUID> loadBoards(){
        ArrayList<UUID> localBoards = new ArrayList<>();

        File file = new File("localBoards");

        if (file.exists()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                localBoards = (ArrayList<UUID>) ois.readObject();
                ois.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(localBoards);
        return localBoards;
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
