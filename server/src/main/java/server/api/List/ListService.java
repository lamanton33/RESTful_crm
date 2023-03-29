package server.api.List;

import commons.Card;
import commons.CardList;
import commons.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Board.BoardService;
import server.api.Card.CardService;
import server.database.ListRepository;

import java.util.List;
import java.util.UUID;

/**
 * Handles business logic of the List endpoints
 */
@Service
public class ListService {

    private final ListRepository listRepository;
    private final CardService cardService;
    private final BoardService boardService;

    /** Initialises the controller using dependency injection */
    @Autowired
    public ListService(ListRepository listRepository, CardService cardService, BoardService boardService) {
        this.boardService = boardService;
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
            var listres = listRepository.save(list);
            boardService.updateBoardAddList(list);
            return Result.SUCCESS.of(listres);
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_LIST;
        }
    }

    /**
     * Deletes the CardList with the given id
     */
    public Result<Object> deleteList(UUID id) {
        try {
            return Result.SUCCESS;
        }catch (Exception e){
            return Result.FAILED_DELETE_LIST;
        }
    }
    /**
     * Updates the name of the CardList with id {id},
     * with the name of the given CardList list.
     */
    public Result<CardList> updateName(CardList list, UUID id) {
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
    public Result<CardList> getListById(UUID id) {
        try {
            return Result.SUCCESS.of(listRepository.findById(id).get());
        }catch (Exception e){
            return Result.FAILED_RETRIEVE_LIST_BY_ID;
        }
    }

    /**
     * Removes a certain card from the list with Id {id}
     */
    public Result<CardList> removeCardFromList(Card card, UUID id){
        cardService.deleteCard(card.cardID);
        return Result.SUCCESS.of(listRepository.findById(id)
                .map(l -> {
                    if(l.cardList.contains(card)) {
                        l.cardList.remove(card);
                    }
                    return listRepository.save(l);
                }).get());
    }


    /** Adds a card to a list in the repo
     * @param card
     * @param listId
     * @return
     */
    public Result<Card> addCardToList(Card card, UUID listId){
        System.out.println("Adding card:\t" +  card.getCardID() + "\tto\t" + listId);
        Result<Card> result = cardService.addNewCard(card);
        if (!result.success) {
            return result.of(null);
        }
        Card newCard = listRepository.findById(listId)
                .map(l -> {
                    if(!l.cardList.contains(card)){
                        l.cardList.add(card);
                    }
                    System.out.println("List with id\t" + listId + "\thas size\t" + l.cardList.size());
                    listRepository.save(l);
                    return card;
                }).get();
        return Result.SUCCESS.of(newCard);
    }
}
