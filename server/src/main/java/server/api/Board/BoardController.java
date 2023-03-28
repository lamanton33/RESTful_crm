
package server.api.Board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import commons.Board;
import commons.Result;
import commons.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * Retrieves all cards from the repository
     */
    @GetMapping({" ","/get-all"})
    public Result<List<Board>> getAllBoards(){
        return boardService.getAllBoards();
    }


    /**
     * Retrieves specific the Board from the repository
     */
    @GetMapping({"/get/{id}"})
    public Result<Board> getBoard(UUID id){
        return boardService.getBoard(id);
    }

    /**
     * Delete request to remove the Card with id {id} from the repository
     */
    @DeleteMapping("/delete/{id}")
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
}