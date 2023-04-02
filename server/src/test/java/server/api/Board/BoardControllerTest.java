package server.api.Board;

import commons.*;
import commons.utils.HardcodedIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    BoardService boardService;
    @Mock
    SimpMessagingTemplate msg;
    @InjectMocks
    BoardController boardController;

    Board board1;



    @BeforeEach
    public void setUp(){
        //init mocks
        MockitoAnnotations.openMocks(this);
        boardController = new BoardController(boardService, msg);
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        board1 = new Board(idGenerator1.generateID(), "Board Title 1", new ArrayList<>(),"Description 1",
                false, "password1", new Theme("#2A2A2A", "#1B1B1B", "#00"));

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
    void getBoard() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(board1)).when(boardService).getBoardById(idGenerator1.generateID());

        Result<Board> result = boardController.getBoard(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(board1), result);
    }
    @Test
    void createNewBoard() {
        doReturn(Result.SUCCESS.of(board1)).when(boardService).addNewBoard(board1);

        Result<Board> result = boardController.createBoard(board1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }
    @Test
    void deleteBoard() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(board1)).when(boardService).deleteBoard(idGenerator1.generateID());

        Result<Board> result = boardController.deleteBoard(idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(board1), result);
    }

    @Test
    void updateBoardTheme() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(board1)).when(boardService).updateBoardTheme(idGenerator1.generateID(), new Theme("#2A2A2A", "#1B1B1B", "#FFFFFF"));

        Result<Board> result = boardController.updateBoardTheme(idGenerator1.generateID(), new Theme("#2A2A2A", "#1B1B1B", "#FFFFFF"));
        assertEquals(Result.SUCCESS.of(board1), result);
    }
    @Test
    void addListToBoard() {
        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        doReturn(Result.SUCCESS.of(board1)).when(boardService).updateBoardAddList(new CardList("List Title 1", new ArrayList<>(), board1));

        Result<Board> result = boardController.addListToBoard(new CardList("List Title 1", new ArrayList<>(), board1), idGenerator1.generateID());
        assertEquals(Result.SUCCESS.of(board1), result);
    }
}