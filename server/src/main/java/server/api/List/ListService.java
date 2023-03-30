package server.api.List;

import commons.Card;
import commons.CardList;
import commons.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Card.CardService;
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
    public Result<List<CardList>> getAll() {
        try {
            return Result.SUCCESS.of(listRepository.findAll());
        }catch (Exception e){
            return Result.FAILED_GET_ALL_LIST;
        }
    }

    /**
     * Adds the CardList list to the repository
     */
    public Result<CardList> addNewList(CardList list) {
        if (list.cardListTitle == null || list.cardList == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            return Result.SUCCESS.of(listRepository.save(list));
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_LIST;
        }
    }

    /**
     * Deletes the CardList with the given id
     */
    public Result<Object> deleteList(Integer id) {
        try {
            listRepository.deleteById(id);
            return Result.SUCCESS;
        }catch (Exception e){
            return Result.FAILED_DELETE_LIST;
        }
    }


    /**
     * Updates the name of the CardList with id {id},
     * with the name of the given CardList list.
     */
    public Result<CardList> updateName(CardList list, Integer id) {
        try {
            return Result.SUCCESS.of(listRepository.findById(id)
                    .map(l -> {
                        l.setCardListTitle(list.cardListTitle);
                        return listRepository.save(l);
                    }).get());
        }catch (Exception e){
            return Result.FAILED_UPDATE_LIST;
        }
    }

    /**
     * Get a list by an id method
     */
    public Result<CardList> getListById(Integer id) {
        try {
            return Result.SUCCESS.of(listRepository.findById(id).get());
        }catch (Exception e){
            return Result.FAILED_RETRIEVE_LIST_BY_ID;
        }
    }

    /**
     * Removes a certain card from the list with Id {id}
     */
    public Result<CardList> removeCardFromList(Card card, Integer id){
        try{
            cardService.deleteCard(card.cardID);
            return Result.SUCCESS.of(listRepository.findById(id)
                    .map( l -> {
                        l.cardList.remove(card);
                        listRepository.save(l);
                        return l;
                    }).get());
        } catch (Exception e){
            return Result.FAILED_REMOVE_CARD_FROM_LIST;
        }
//
//        cardService.deleteCard(card.cardID);
//        return Result.SUCCESS.of(listRepository.findById(id)
//                .map(l -> {
//                    l.cardList.remove(card);
//                    return listRepository.save(l);
//                }).get());
    }

    /**
     * Adds the given card to the list with Id {id}
     */
    public Result<Card> addCardToList(Card card, Integer id){
        try{
            Result<Card> result = cardService.addNewCard(card);
            return Result.SUCCESS.of(listRepository.findById(id)
                    .map(l -> {
                        if(!l.cardList.contains(card)){
                            l.cardList.add(card);
                        }
                        listRepository.save(l);
                        return result.value;
                    }).get());
        } catch (Exception e){
            return Result.OBJECT_ISNULL.of(null);
        }
//        var list = listRepository.findById(id).get();
//        card.cardList = list;
//        var result = cardService.addNewCard(card);
//        if (!result.success) {
//            return result.of(null);
//        }
//        list.addCard(card);
//        listRepository.save(list);
//        return Result.SUCCESS.of(card);
    }

    /**
     * @param card the card to move
     * @param idFrom the id of the list the card is currently in
     * @param idTo the id of the list the card is moving to
     * @return the card that was moved
     * <p>
     * Moves a card from one list to another.
     * <p>
     * Be very careful with this method, it is very easy to break the database
     * as there is a bidirectional reference between the card and the cardList.
     *
     */
    public Result<Card> moveCard(Card card, Integer idFrom, Integer idTo,int indexTo) {
        if(card == null || idFrom == null || idTo == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            CardList oldCardList = listRepository.findById(idFrom).get();
            CardList newCardList = listRepository.findById(idTo).get();

            oldCardList.cardList.remove(card);

            card.cardList = newCardList;
            card.cardListId = newCardList.cardListID;

            cardService.updateCard(card,card.cardID);

            newCardList.cardList.add(indexTo,card);
            listRepository.save(newCardList);
            listRepository.save(oldCardList);

            return Result.SUCCESS.of(card);
        }
        catch (Exception e) {
            return Result.FAILED_MOVE_CARD;
        }
    }
}
