package server.api.Card;

import commons.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.List;

/**
 * Handles logic of the Card endpoints
 */
@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Retrieves all the Cards from the repository
     */
    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    /**
     * Adds the Card to the repository
     * @param card
     */
    public Card addNewCard (Card card){
        if (card.cardTitle == null) {
            return null;
        }
        return cardRepository.save(card);
    }

    /**
     * Deletes the Card with the given id
     * @param id
     */
    public void deleteCard (Integer id) {
        cardRepository.deleteById(id);
    }

    /**
     * Updates the name of the Card with specific id {id},
     * with the name of the given Card card.
     * @param card
     * @param id
     */
    public Card updateName (Card card, Integer id){
        return cardRepository.findById(id)
                .map(l -> {
                    l.setCardTitle(card.cardTitle);
                    return cardRepository.save(l);
                })
                .orElseGet(() -> {
                    return cardRepository.save(card);
                });
    }

    /**
     * Get a card by an id method
     * @param id
     * @return card with specific ID
     */
    public Card getCardById(Integer id){
        return cardRepository.findById(id).get();
    }



}
