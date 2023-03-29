package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;


    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    /**
     * Retrieves all cards from the repository
     */
    @GetMapping({" ","/get-all"})
    public Result<List<Board>> getAllBoards(){
        return boardService.getAllBoards();
    }


    /**
     * Delete request to remove the Card with id {id} from the repository
     */
    @DeleteMapping("/delete/{id}")
    public Result<Board> deleteBoard(@PathVariable Integer id) {
        return boardService.deleteBoard(id);
    }


    /**
     * Put request to update the theme of a board with id {id}
     */
    @RequestMapping("/update-theme/{id}")
    public Result<Board> updateBoardTheme(@PathVariable Integer id, @RequestBody Theme theme){
        return boardService.updateBoardTheme(id, theme);
    }

}