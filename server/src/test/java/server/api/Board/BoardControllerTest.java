package server.api.Board;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.Card.CardController;
import server.api.Card.CardService;
import server.api.List.ListController;
import server.api.List.ListService;
import server.api.Task.TaskService;
import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ListRepository;
import server.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @Mock
    BoardRepository boardRepository;
    @Mock
    ListRepository listRepository;
    @Mock
    ListService listService;
    @Mock
    BoardService boardService;
    @InjectMocks
    BoardController boardController;

    Board board1;



    @BeforeEach
    public void setUp(){
        //init mocks
        MockitoAnnotations.openMocks(this);
        boardController = new BoardController(boardService);
        board1 = new Board("Test Board 1", 1, new ArrayList<>(), "Description1", false,
                "password1", new Theme(1, "#2A2A2A", "#1B1B1B", "#00"));

    }

    @Test
    void getAllBoards() {
        List<Board> allBoards = new ArrayList<>();
        allBoards.add(board1);

        doReturn(Result.SUCCESS.of(allBoards)).when(boardService).getAllBoards();

        Result<List<Board>> result = boardController.getAllBoards();
        assertEquals(Result.SUCCESS.of(allBoards), result);
    }
    @Test
    void getBoardById() {
        doReturn(Result.SUCCESS.of(board1)).when(boardService).getBoardById(1);

        Result<Board> result = boardController.getBoardById(1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }
    @Test
    void createNewBoard() {
        doReturn(Result.SUCCESS.of(board1)).when(boardService).addNewBoard(board1);

        Result<Board> result = boardController.createNewBoard(board1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }
    @Test
    void deleteBoard() {
        doReturn(Result.SUCCESS.of(board1)).when(boardService).deleteBoard(1);

        Result<Board> result = boardController.deleteBoard(1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }

    @Test
    void updateBoardTheme() {
        doReturn(Result.SUCCESS.of(board1)).when(boardService).updateBoardTheme(1, new Theme(1, "#2A2A2A", "#1B1B1B", "#FFFFFF"));

        Result<Board> result = boardController.updateBoardTheme(1, new Theme(1, "#2A2A2A", "#1B1B1B", "#FFFFFF"));
        assertEquals(Result.SUCCESS.of(board1), result);
    }
}