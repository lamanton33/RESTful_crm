package server.api.Board;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping({" ","/"})
    public Board getAllCards(){
        return Board.createDummyBoard();
    }

}
