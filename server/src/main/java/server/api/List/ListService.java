package server.api.List;

import commons.Card;
import commons.CardList;
import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Card.CardService;
import server.api.Task.TaskService;
import server.database.ListRepository;

import java.util.List;

/**
 * Handles business logic of the List endpoints
 */
@Service
public class ListService {

    private final ListRepository listRepository;
    private final CardService cardService;

    @Autowired
    public ListService(ListRepository listRepository, CardService cardService) {
        this.listRepository = listRepository;
        this.cardService = cardService;
    }

    /**
     * Retrieves all the CardList from the repository
     */
    public List<CardList> getAll() {
        return listRepository.findAll();
    }

    /**
     * Adds the CardList list to the repository
     */
    public CardList addNewList(CardList list) {
        if (list.cardListTitle == null || list.cardList == null) {
            return null;
        }
        return listRepository.save(list);
    }

    /**
     * Deletes the CardList with the given id
     */
    public void deleteList(Integer id) {
        listRepository.deleteById(id);
    }


    /**
     * Updates the name of the CardList with id {id},
     * with the name of the given CardList list.
     */
    public CardList updateName(CardList list, Integer id) {
        return listRepository.findById(id)
                .map(l -> {
                    l.setCardListTitle(list.cardListTitle);
                    return listRepository.save(l);
                }).get();
    }

    /**
     * Get a list by an id method
     */
    public CardList getListById(Integer id) {
        return listRepository.findById(id).get();
    }

    /**
     * Removes a certain card from the list with Id {id}
     */
    public CardList removeCardFromList(Card card, Integer id){
        cardService.deleteCard(card.cardID);
        return listRepository.findById(id)
                .map(l -> {
                    if(l.cardList.contains(card)) {
                        l.cardList.remove(card);
                    }
                    return listRepository.save(l);
                }).get();
    }

    /**
     * Adds the given card to the list with Id {id}
     */
    public Card addCardToList(Card card, Integer id){
        cardService.addNewCard(card);
        return listRepository.findById(id)
                .map(l -> {
                    if(!l.cardList.contains(card)){
                        l.cardList.add(card);
                    }
                    listRepository.save(l);
                    return card;
                }).get();
    }
}
