package server.api.Card;

import commons.Card;
import commons.Result;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * @param cardId
     */
    @GetMapping({"/get/{cardId}"})
    public Result<Card> getCardById(@PathVariable UUID cardId) {
        return cardService.getCardById(cardId);
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
    @DeleteMapping("/delete/{cardId}")
    public Result<Object> deleteCard(@PathVariable UUID cardId) {
        msg.convertAndSend("/topic/update-card/", cardId);
        return cardService.deleteCard(cardId);
    }

    /**
     * Put request to update the CardList with id {id}
     */
    @PutMapping("/change-name/{cardId}")
    public Result<Object> changeCardName(@RequestBody Card card, @PathVariable UUID cardId){
        msg.convertAndSend("/topic/update-card/", cardId);
        return cardService.updateName(card, cardId);
    }

    /**
     * Removes the task in the request body
     * from the Card with the given id
     */
    @PutMapping("/remove-task/{cardId}")
    public Result<Card> removeTaskFromCard(@RequestBody Task task, @PathVariable UUID cardId){
        msg.convertAndSend("/topic/update-card/", cardId);
        return cardService.removeTaskFromCard(task, cardId);
    }

    /**
     * Adds the task in the request body
     * to the card with given id
     */
    @PutMapping("/add-task/{cardId}")
    public Result<Card> addTaskToCard(@RequestBody Task task, @PathVariable UUID cardId){
        msg.convertAndSend("/topic/update-card/", cardId);
        return cardService.addTaskToCard(task, cardId);
    }


}
