package server.api.Card;

import commons.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/card")
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
    public Card addNewCard (@RequestBody Card card) {
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
    public void updateCardName(@RequestBody Card card, @PathVariable Integer id){
        cardService.updateCardName(card, id);
    }

    /**
     * Removes the task in the request body
     * from the Card with the given id
     */
    @PutMapping("/removeTask/{id}")
    public Card removeTaskFromCard(@RequestBody Task task, @PathVariable Integer id){
        return cardService.removeTaskFromCard(task, id);
    }

    /**
     * Adds the task in the request body
     * to the card with given id
     */
    @PutMapping("/addTask/{id}")
    public Card addTaskToCard(@RequestBody Task task, @PathVariable Integer id){
        return cardService.addTaskToCard(task, id);
    }


}
