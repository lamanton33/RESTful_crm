package server.api.Tag;

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
import server.api.Board.BoardController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {
    @Mock
    TagService tagService;
    @Mock
    SimpMessagingTemplate msg;
    @InjectMocks
    TagController tagController;

    Tag tag1;
    Tag tag2;
    Card card1;
    CardList list1;

    @BeforeEach
    public void setUp(){
        //init mocks
        MockitoAnnotations.openMocks(this);
        tagController = new TagController(tagService, msg);

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");

        tag1 = new Tag(idGenerator1.generateID(), "Test Tag 1", "#000000");

        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");

        tag2 = new Tag(idGenerator1.generateID(), "Test Tag 2", "#FFFFFF");
        list1 = new CardList(idGenerator1.generateID(), "Test List",
                new ArrayList<>(), new Board());
        card1 = new Card(idGenerator1.generateID(), list1, "Test Card 1", "Test Description 1", new ArrayList<>(), List.of(tag1));
        tag1.card = card1;
        tag1.cardId = card1.cardID;
    }

    @Test
    void updateTag() {
        doReturn(Result.SUCCESS.of(tag1)).when(tagService).updateTag(tag1, tag2.tagID);

        assertEquals(Result.SUCCESS.of(tag1), tagController.updateTag(tag1, tag2.tagID));
    }
}