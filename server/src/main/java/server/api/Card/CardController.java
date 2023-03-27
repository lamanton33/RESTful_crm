package server.api.Card;

import commons.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final CardService cardService;
    private final SimpMessagingTemplate msg;

    @Autowired
    public CardController(CardService cardService, SimpMessagingTemplate msg){
        this.cardService = cardService;
        this.msg = msg;
    }

    /**
     * Retrieves all cards from the repository
     */
    @GetMapping({" ","/get-all"})
    public Result<List<Card>> getAllCards(){
        return cardService.getAllCards();
    }

    /**
     * Retrieves the Card with the given id from the repository
     * @param id
     */
    @GetMapping({"/get/{id}"})
    public Result<Card> getCardById(@PathVariable Integer id) {
        return cardService.getCardById(id);
    }

    /**
     * Post request to add the card in the request body to the repository
     * @param card
     */
    @PostMapping("/create/")
    public Result<Card> createNewCard (@RequestBody Card card) {
        msg.convertAndSend("/topic/update-card/", card.cardID);
        return cardService.addNewCard(card);
    }

    /**
     * Delete request to remove the Card with id {id} from the repository
     */
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteCard(@PathVariable Integer id) {
        msg.convertAndSend("/topic/update-card/", id);
        return cardService.deleteCard(id);
    }

    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/change-name/{id}")
    public Result<Object> changeCardName(@RequestBody Card card, @PathVariable Integer id){
        msg.convertAndSend("/topic/update-card/", id);
        return cardService.updateName(card, id);
    }

    /**
     * Removes the task in the request body
     * from the Card with the given id
     */
    @PutMapping("/remove-task/{id}")
    public Result<Card> removeTaskFromCard(@RequestBody Task task, @PathVariable Integer id){
        msg.convertAndSend("/topic/update-card/", id);
        return cardService.removeTaskFromCard(task, id);
    }

    /**
     * Adds the task in the request body
     * to the card with given id
     */
    @PutMapping("/add-task/{id}")
    public Result<Card> addTaskToCard(@RequestBody Task task, @PathVariable Integer id){
        msg.convertAndSend("/topic/update-card/", id);
        return cardService.addTaskToCard(task, id);
    }


}
