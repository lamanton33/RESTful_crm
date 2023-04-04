package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final SimpMessagingTemplate msg;

    @Autowired
    public BoardController(BoardService boardService, SimpMessagingTemplate msg) {
        this.boardService = boardService;
        this.msg = msg;
    }

    /**
     * Retrieves all boards from the repository
     */
    @GetMapping({" ","/get-all"})
    public Result<List<Board>> getAllBoards(){
        return boardService.getAllBoards();
    }


    /**
     * Retrieves specific the Board from the repository
     */
    @GetMapping({"/get/{id}"})
    public Result<Board> getBoard(@PathVariable UUID id){
        return boardService.getBoardById(id);
    }

    /**
     * Retrieves specific the Board from the repository
     */
    @PostMapping({"/create/"})
    public Result<Board> createBoard(@RequestBody Board board){
        System.out.println("Created a board with the id \t" + board.getBoardID());
        msg.convertAndSend("/topic/update-board/", board.boardID);
        return boardService.addNewBoard(board);
    }

    /**
     * @param board the board to update
     * @param id  the id of the board to update
     * @return the updated board
     */
    @PostMapping({"/update/{id}"})
    public Result<Board> updateBoard(@RequestBody Board board, @PathVariable UUID id){
        System.out.println("Updated board with the id \t" + board.getBoardID());
        msg.convertAndSend("/topic/update-board/", id);
        return boardService.updateBoard(board, id);
    }


    /**
     * Delete request to remove the Board with id {id} from the repository
     */
    @PutMapping("/delete/{id}")
    public Result<Board> deleteBoard(@PathVariable UUID id) {
        msg.convertAndSend("/topic/update-board/", id);
        return boardService.deleteBoard(id);
    }

    /**
     * Put request to update the theme of a board with id {id}
     */
    @PutMapping("/update-theme/{id}")
    public Result<Board> updateBoardTheme(@PathVariable UUID id, @RequestBody Theme theme){
        msg.convertAndSend("/topic/update-board/", id);
        return boardService.updateBoardTheme(id, theme);
    }

    /**
     * Adds the given list to board with id {id}
     */
    @PutMapping("/add-list/{id}")
    public Result<Board> addListToBoard(@RequestBody CardList list, @PathVariable UUID id){
        msg.convertAndSend("/topic/update-board/", id);
        return boardService.updateBoardAddList(list);
    }
}