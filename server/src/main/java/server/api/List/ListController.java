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
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteList(@PathVariable UUID id) {
        msg.convertAndSend("/topic/update-card/", id);
        return listService.deleteList(id);
    }


    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/update/{id}")
    public Result<CardList> updateName(@RequestBody CardList list, @PathVariable UUID id) {
        msg.convertAndSend("/topic/update-card/", id);
        return listService.updateName(list, id);
    }

    /**
     * Updates the cardList with ID {id} by deleting the given card from it.
     */
    @PutMapping("/delete-card/{cardId}")
    public Result<CardList> removeCardFromList(@RequestBody Card card, @PathVariable UUID cardId){
        return listService.removeCardFromList(card, cardId);
    }

    /**
     * Adds the given card to list with id {id}
     */
    @PutMapping("/add-card/{listId}")
    public Result<Card> addCardToList(@RequestBody Card card, @PathVariable UUID listId){
        System.out.println("Received a request to add card\t" + card.getCardID() + " to list\t"+listId);
        msg.convertAndSend("/topic/update-cardlist/", listId);
        return listService.addCardToList(card, listId);
    }

    /**
     * Moves the given card from the list with id {id_from},
     * to the list with id {id_to}
     */
    @PutMapping("/move-card/{idFrom}/{idTo}/{indexTo}")
    public Result<Card> moveCard(@RequestBody Card card, @PathVariable UUID idFrom,
                                 @PathVariable UUID idTo, @PathVariable Integer indexTo){
        return listService.moveCard(card,idFrom ,idTo, indexTo);
    }
}
