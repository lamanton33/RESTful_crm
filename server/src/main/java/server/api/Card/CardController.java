package server.api.Card;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.logging.log4j.util.LambdaUtil.getAll;

@RestController
@RequestMapping("/api/Card")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

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


}