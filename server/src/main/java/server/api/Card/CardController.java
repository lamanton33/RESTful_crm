package server.api.Card;

import commons.Card;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/Card")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    /**
     * Retrieves all cards from the repository
     */
    @GetMapping({" ","/"})
    public List<Card> getAllCards(){
        return cardService.getAllCards();
    }

    /**
     * Retrieves the Card with the given id from the repository
     * @param id
     */
    @GetMapping({"id"})
    public Card getCardById(@PathVariable Integer id) {
        return cardService.getCardById(id);
    }

    /**
     * Post request to add the card in the request body to the repository
     * @param card
     */
    @PostMapping({" ", "/"})
    public Card createNewCard (@RequestBody Card card) {
        return cardService.addNewCard(card);
    }

    /**
     * Delete request to remove the Card with id {id} from the repository
     */
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Integer id) {
        cardService.deleteCard(id);
    }

    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/{id}")
    public void changeCardName(@RequestBody Card card, @PathVariable Integer id){
        cardService.updateName(card, id);
    }

    /**
     * Removes the task in the request body
     * from the Card with the given id
     */
    @PutMapping("/{id}/removeTask")
    public Card removeTaskFromCard(@RequestBody Task task, @PathVariable Integer id){
        return cardService.removeTask(task, id);
    }

    /**
     * Adds the task in the request body
     * to the card with given id
     */
    @PutMapping("/{id}/addTask")
    public Card addTaskToCard(@RequestBody Task task, @PathVariable Integer id){
        return cardService.addTask(task, id);
    }


}
