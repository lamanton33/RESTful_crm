package server.api.List;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Request handle for the CardList endpoints
 */
@RestController
@RequestMapping("/api/list")
public class ListController {

    private final ListService listService;


    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
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
    public Result<CardList> getListById(@PathVariable Integer id){
        return listService.getListById(id);
    }

    /**
     * Post request to add the list in the request body to the repository
     */
    @PostMapping("/create/")
    public Result<CardList> createNewList(@RequestBody CardList list){
        return listService.addNewList(list);
    }


    /**
     * Delete request to remove the CardList with id {id} from the repository
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteList(@PathVariable Integer id) {
        return listService.deleteList(id);
    }


    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/update/{id}")
    public Result<CardList> updateName(@RequestBody CardList list, @PathVariable Integer id) {
        return listService.updateName(list, id);
    }

    /**
     * Updates the cardList with ID {id} by deleting the given card from it.
     */
    @PutMapping("/delete-card/{id}")
    public Result<CardList> removeCardFromList(@RequestBody Card card, @PathVariable Integer id){
        return listService.removeCardFromList(card, id);
    }

    /**
     * Adds the given card to list with id {id}
     */
    @PutMapping("/add-card/{id}")
    public Result<Card> addCardToList(@RequestBody Card card, @PathVariable Integer id){
        return listService.addCardToList(card, id);
    }

    /**
     * Moves the given card from the list with id {id_from},
     * to the list with id {id_to}
     */
    @PutMapping("/move-card/{idFrom}/{idTo}/{indexTo}")
    public Result<Card> moveCard(@RequestBody Card card, @PathVariable Integer idFrom,
                                 @PathVariable Integer idTo, @PathVariable Integer indexTo){
        return listService.moveCard(card,idFrom ,idTo, indexTo);
    }
}
