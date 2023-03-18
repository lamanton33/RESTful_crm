package server.api.List;

import commons.Card;
import commons.CardList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.ListRepository;

import java.util.List;

/**
 * Handles business logic of the List endpoints
 */
@Service
public class ListService {

    private final ListRepository listRepository;

    @Autowired
    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
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
                })
                .orElseGet(() -> {
                    return listRepository.save(list);
                });
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
    public CardList removeCard(Card card, Integer id){
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
    public CardList addCard(Card card, Integer id){
        return listRepository.findById(id)
                .map(l -> {
                    if(!l.cardList.contains(card)){
                        l.cardList.add(card);
                    }
                    return listRepository.save(l);
                }).get();
    }
}
