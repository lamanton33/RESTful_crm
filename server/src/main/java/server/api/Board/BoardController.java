package server.api.Board;

import com.sun.xml.bind.v2.TODO;
import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import server.api.Card.CardService;

import java.util.ArrayList;
import java.util.List;

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
    @MessageMapping("/boards/get-dummy-board")
    @SendTo("topic/boards")
    public Result<Board> getDummyBoard(){
        System.out.println("Received call");
        return Result.SUCCESS.of(Board.createDummyBoard());
    }

    //TODO

}
