package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Retrieves the Board with the given id
     * @param id
     */
    public Result<Board> getBoardById(Integer id){
        try {
            Optional<Board> board = boardRepository.findById(id);
            if (board.isPresent()) {
                return Result.SUCCESS.of(board.get());
            } else {
                return Result.FAILED_RETRIEVE_BOARD_BY_ID.of(null);
            }
        }catch (Exception e){
            return Result.FAILED_RETRIEVE_BOARD_BY_ID.of(null);
        }
    }

    /**
     * Adds the Board to the repository
     * @param board
     */
    public Result<Board> addNewBoard(Board board){
        if (board == null || board.boardTitle == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            boardRepository.save(board);
            return Result.SUCCESS.of(board);
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_BOARD.of(null);
        }
    }



    /**
     * Deletes the Board with the given id
     * @param id
     */
    public Result<Board> deleteBoard (Integer id) {
        try {
            boardRepository.deleteById(id);
            return Result.SUCCESS.of(null);
        } catch (Exception e){
            return Result.FAILED_DELETE_BOARD;
        }
    }

    /**
     * Updates the theme of the board with the given id.
     */
    public Result<Board> updateBoardTheme(Integer id, Theme theme){
        try {
            return Result.SUCCESS.of(boardRepository.findById(id)
                    .map(b -> {
                        b.setBoardTheme(theme);
                        boardRepository.save(b);
                        return b;
                    }).get());
        }catch (Exception e){
            return Result.FAILED_UPDATE_BOARD_THEME;
        }
    }
}
