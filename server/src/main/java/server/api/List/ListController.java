package server.api.List;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Request handle for the CardList endpoints
 */
@RestController
@RequestMapping("/api/list")
public class ListController {

    private final ListService listService;
    private final SimpMessagingTemplate msg;


    @Autowired
    public ListController(ListService listService, SimpMessagingTemplate msg) {
        this.listService = listService;
        this.msg = msg;
    }

    /**
     * Retrieves all the CardLists from the repository
     */
    @GetMapping({" ", "/get-all/"})
    public Result<List<CardList>> getAllLists(){
        return listService.getAll();
    }

    /**
     * Retrieves the CardList with the given id from the repository
     */
    @GetMapping({"/get/{id}"})
    public Result<CardList> getListById(@PathVariable UUID id){
        return listService.getListById(id);
    }

    /**
     * Post request to add the list in the request body to the repository
     */
    @PostMapping("/create/")
    public Result<CardList> createNewList(@RequestBody CardList list){
        System.out.println("Creating a list with the id:  \t" + list.getCardListId() + "\ton board:\t" + list.boardId);
        Result<CardList> result = listService.addNewList(list);
        if(result.success){
            msg.convertAndSend("/topic/update-board/", list.boardId);
        }else{
            return Result.FAILED_ADD_NEW_LIST.of(null);
        }
        return result;
    }


    /**
     * Delete request to remove the CardList with id {id} from the repository
     */
    @PostMapping("/delete/{id}")
    public Result<Object> deleteList(@PathVariable UUID id, @RequestBody CardList list) {
        var result = listService.deleteList(id);
        msg.convertAndSend("/topic/update-board/", list.boardId);
        return result;
    }

    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/update/{id}")
    public Result<CardList> updateName(@RequestBody CardList list, @PathVariable UUID id) {
        var result = listService.updateName(list, id);
        msg.convertAndSend("/topic/update-board/", list.boardId);
        return result;
    }

    /**
     * Updates the cardList with ID {id} by deleting the given card from it.
     */
    @PutMapping("/delete-card/{cardListId}")
    public Result<CardList> removeCardFromList(@RequestBody Card card, @PathVariable UUID cardListId){
        var result = listService.removeCardFromList(card, cardListId);
        msg.convertAndSend("/topic/update-cardlist/", card.cardListId);
        msg.convertAndSend("/topic/update-card/", card.cardID);
        return result;
    }

    /**
     * Adds the given card to list with id {id}
     */
    @PutMapping("/add-card/{listId}")
    public Result<Card> addCardToList(@RequestBody Card card, @PathVariable UUID listId){
        System.out.println("Received a request to add card\t" + card.getCardID() + " to list\t"+listId);
        var result = listService.addCardToList(card, listId);
        msg.convertAndSend("/topic/update-cardlist/", listId);
        return result;
    }

    /**
     * Moves the given card from the list with id {id_from},
     * to the list with id {id_to}
     */
    @PutMapping("/move-card/{idFrom}/{idTo}/{indexTo}")
    public Result<Card> moveCard(@RequestBody Card card, @PathVariable UUID idFrom,
                                 @PathVariable UUID idTo, @PathVariable Integer indexTo){
        Result<CardList> res = listService.getListById(idTo);
        if(res.success && res != null){
            UUID boardId = res.value.boardId;
            var result = listService.moveCard(card,idFrom ,idTo, indexTo);
            msg.convertAndSend("/topic/update-board/", boardId);
            return result;
        }
        return Result.FAILED_MOVE_CARD.of(null);
    }
}
