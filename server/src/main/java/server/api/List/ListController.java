package server.api.List;


import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Request handle for the CardList endpoints
 */
@RestController
@RequestMapping("/api/List")
public class ListController {

    private final ListService listService;


    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    /**
     * Retrieves all the CardLists from the repository
     */
    @GetMapping({" ", "/"})
    public List<CardList> getAllLists(){
        return listService.getAll();
    }

    /**
     * Retrieves the CardList with the given id from the repository
     */
    @GetMapping({"id"})
    public CardList getListById(@PathVariable Integer id){
        return listService.getListById(id);
    }

    /**
     * Post request to add the list in the request body to the repository
     */
    @PostMapping({" ", "/"})
    public CardList createNewList(@RequestBody CardList list){
        return listService.addNewList(list);
    }


    /**
     * Delete request to remove the CardList with id {id} from the repository
     */
    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Integer id) {
        listService.deleteList(id);
    }


    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/{id}")
    public CardList changeListName(@RequestBody CardList list, @PathVariable Integer id){
        return listService.updateName(list, id);
    }

    /**
     * Updates the cardList with Id {id} by deleting the given card from it.
     */
    @PutMapping("/{id}/deleteCard")
    public CardList deleteCard(@RequestBody Card card, @PathVariable Integer id){
        return listService.removeCard(card, id);
    }

    /**
     * Adds the given card to list with id {id}
     */
    @PutMapping("/{id}/addCard")
    public CardList addCard(@RequestBody Card card, @PathVariable Integer id){
        return listService.addCard(card, id);
    }

    /**
     * Moves the given card from the list with id {id_from},
     * to the list with id {id_to}
     */
    @PutMapping("/{idFrom}/{idTo}/moveCard")
    public CardList moveCard(@RequestBody Card card, @PathVariable Integer idFrom, @PathVariable Integer idTo){
        listService.removeCard(card, idFrom);
        return listService.addCard(card, idTo);
    }

}
