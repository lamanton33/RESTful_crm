package server.api.List;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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


    /** Sends card lists to the client via websockets connection
     * @param cardList
     * @return CardList
     */


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
        Result<CardList> result = listService.addNewList(list);
        if(result.success){
            msg.convertAndSend("/app/update-board/", list.cardListID);
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
    @PutMapping("/delete-card/{id}")
    public Result<CardList> removeCardFromList(@RequestBody Card card, @PathVariable UUID id){
        return listService.removeCardFromList(card, id);
    }

    /**
     * Adds the given card to list with id {id}
     */
    @PutMapping("/add-card/{id}")
    public Result<CardList> addCardToList(@RequestBody Card card, @PathVariable UUID id){
        msg.convertAndSend("/topic/update-card/", id);
        return listService.addCardToList(card, id);
    }

    /**
     * Moves the given card from the list with id {id_from},
     * to the list with id {id_to}
     */
    @PutMapping("/move-card/{idFrom}/{idTo}")
    public Result<CardList> moveCard(@RequestBody Card card, @PathVariable UUID idFrom, @PathVariable UUID idTo){
        msg.convertAndSend("/topic/update-card/", idTo);
        listService.removeCardFromList(card, idFrom);
        return listService.addCardToList(card, idTo);
    }

}
