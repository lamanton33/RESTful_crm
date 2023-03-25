package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.BoardRepository;

import java.util.List;
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
//
//    /**
//     * Adds the Board to the repository
//     * @param board
//     */
//    public Result<Board> addNewBoard (Board board){
//        if (board == null || board.boardTitle == null) {
//            return Result.OBJECT_ISNULL.of(null);
//        }
//        try {
//            return Result.SUCCESS.of(boardRepository.save(board));
//        }catch (Exception e){
//            return Result.FAILED_ADD_NEW_BOARD.of(null);
//        }
//    }

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
                        return boardRepository.save(b);
                    }).get());
        }catch (Exception e){
            return Result.FAILED_UPDATE_BOARD_THEME;
        }
    }


}
