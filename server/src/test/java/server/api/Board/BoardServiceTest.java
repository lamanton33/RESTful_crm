package server.api.Board;

import commons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    BoardRepository boardRepository;
    @InjectMocks
    BoardService boardService;

    Board board1;
    Board board2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        boardService = new BoardService(boardRepository);

        board1 = new Board("Test Board 1", 1, new ArrayList<>(), "Description1", false,
                "password1", new Theme(1, "#2A2A2A", "#1B1B1B", "#00"));
        board2 = new Board("Test Board 2", 2, new ArrayList<>(), "Description2", false,
                "password2", new Theme());
    }

    @Test
    void getAllBoards() {
        List<Board> allBoards = new ArrayList<>();
        allBoards.addAll(List.of(board1,board2));

        doReturn(List.of(board1,board2)).when(boardRepository).findAll();

        Result<List<Board>> result = boardService.getAllBoards();
        assertEquals(Result.SUCCESS.of(allBoards), result);
    }

    @Test
    void getAllBoardsFAIL() {
        doThrow(new RuntimeException()).when(boardRepository).findAll();

        Result<List<Board>> result = boardService.getAllBoards();
        assertEquals(Result.FAILED_GET_ALL_BOARDS.of(null), result);
    }

    @Test
    void getBoardById() {
        doReturn(Optional.of(board1)).when(boardRepository).findById(1);

        Result<Board> result = boardService.getBoardById(1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }

    @Test
    void getBoardByIdFAILNotPresent() {
        doReturn(Optional.empty()).when(boardRepository).findById(1);

        Result<Board> result = boardService.getBoardById(1);
        assertEquals(Result.FAILED_RETRIEVE_BOARD_BY_ID.of(null), result);
    }

    @Test
    void getBoardByIdFAILException() {
        doThrow(new RuntimeException()).when(boardRepository).findById(42);

        Result<Board> result = boardService.getBoardById(42);
        assertEquals(Result.FAILED_RETRIEVE_BOARD_BY_ID.of(null), result);
    }
    @Test
    void addNewBoard(){
        doReturn(board1).when(boardRepository).save(board1);

        Result<Board> result = boardService.addNewBoard(board1);
        assertEquals(Result.SUCCESS.of(board1), result);
    }

    @Test
    void addNewBoardNull() {
        Result<Board> result = boardService.addNewBoard(null);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void addNewBoardFAIL() {
        doThrow(new RuntimeException()).when(boardRepository).save(board1);

        Result<Board> result = boardService.addNewBoard(board1);
        assertEquals(Result.FAILED_ADD_NEW_BOARD.of(null), result);
    }

    @Test
    void deleteBoard() {
        Result<Board> result = boardService.deleteBoard(1);
        assertEquals(Result.SUCCESS.of(null), result);
    }

    @Test
    void deleteBoardFAIL() {
        doThrow(new RuntimeException()).when(boardRepository).deleteById(1);

        Result<Board> result = boardService.deleteBoard(1);
        assertEquals(Result.FAILED_DELETE_BOARD.of(null), result);
    }

    @Test
    void updateBoardTheme() {
        Board boardTheme = new Board("Test Board 1", 1, new ArrayList<>(), "Description1", false,
                "password1", new Theme(1, "#2A2A2A", "#1B1B1B", "#FFFFFF"));
        doReturn(Optional.of(boardTheme)).when(boardRepository).findById(1);

        Result<Board> result = boardService.updateBoardTheme(1, new Theme(1, "#2A2A2A", "#1B1B1B", "#00"));
        assertEquals(Result.SUCCESS.of(board1), result);
    }

    @Test
    void updateBoardThemeFAIL() {
        doThrow(new RuntimeException()).when(boardRepository).findById(board1.boardID);

        Result<Board> result = boardService.updateBoardTheme(1, new Theme(1, "#2A2A2A", "#1B1B1B", "#FFFFFF"));
        assertEquals(Result.FAILED_UPDATE_BOARD_THEME.of(null), result);
    }

}