
package server.api.Board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Board;
import commons.Result;
import commons.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@JsonIgnoreProperties({"listID"})
@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    /**
     * Retrieves all cards from the repository
     */
    @MessageMapping("/boards/get-dummy-board/")
    @SendTo("/topic/boards")
    public Result<Board> getDummyBoard(){

        System.out.println("Received call");
        Board b = Board.createDummyBoard();
        b.getCardListByID(2).setCardListTitle("AMOGUS");
        String boardString = serialization(b);


        return Result.SUCCESS.of(Board.createDummyBoard());
    }

    /**
     * @param object
     * @return serialized String
     */
    public <T> String serialization(T object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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