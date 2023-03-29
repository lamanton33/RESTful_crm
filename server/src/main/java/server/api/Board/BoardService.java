package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Retrieves all the Boards from the repository
     */
    public Result<List<Board>> getAllBoards(){
        try {
            return Result.SUCCESS.of(boardRepository.findAll());
        }catch (Exception e){
            return Result.FAILED_GET_ALL_BOARDS;
        }
    }


    /** Getter for board
     * @param boardId the board id you want
     * @return result<board> Result object containing Board
     */
    public Result<Board> getBoard(UUID boardId){
        try{
            return Result.SUCCESS.of(boardRepository.findById(boardId).get());
        }
        catch (Exception e){
            return Result.FAILED_TO_GET_BOARD_BY_ID;
        }
    }

    /**
     * Add a Board to the repository
     * @param board
     */
    public Result<Board> addNewBoard (Board board){
        if (board == null || board.boardTitle == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            return Result.SUCCESS.of(boardRepository.save(board));
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_BOARD.of(null);
        }
    }

    /**
     * Deletes the Board with the given id
     * @param boardId
     */
    public Result<Board> deleteBoard (UUID boardId) {
        try {
            boardRepository.deleteById(boardId);
            return Result.SUCCESS.of(null);
        } catch (Exception e){
            return Result.FAILED_DELETE_BOARD;
        }
    }

    /**
     * Updates the theme of the board with the given id.
     */
    public Result<Board> updateBoardTheme(UUID id, Theme theme){
        try {
            return Result.SUCCESS.of(boardRepository.findById(id)
                    .map(b -> {
                        b.setBoardTheme(theme);
                        return boardRepository.save(b);
                    }).get());
        }catch (Exception e){
            return Result.FAILED_UPDATE_BOARD_THEME;
        }
    }

    /**
     * Updates a board by adding a new list
     */
    public Result<Board> updateBoardAddList(CardList list ,UUID id){
        try {
            return Result.SUCCESS.of(boardRepository.findById(list.boardId)
                    .map(b -> {
                        b.getCardListList().add(list);
                        return boardRepository.save(b);
                    }).get());
        }catch (Exception e){
            return Result.FAILED_TO_ADD_LIST_TO_BOARD;
        }
    }


}
