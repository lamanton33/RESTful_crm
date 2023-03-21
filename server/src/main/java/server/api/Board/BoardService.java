package server.api.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Task.TaskService;
import server.database.BoardRepository;
import server.database.CardRepository;

@Service
public class BoardService {

    private final BoardRepository boardRepository ;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


}
