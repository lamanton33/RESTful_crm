package server.api.List;

import commons.Card;
import commons.CardList;
import commons.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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
        System.out.println("Board id in list service: " + list.board.boardID);
        if (list.cardListTitle == null || list.cardList == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            boardService.updateBoardAddList(list, list.board.boardID);
            return Result.SUCCESS.of(listRepository.save(list));
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

    /**
     * Adds the given card to the list with Id {id}
     */
    public Result<CardList> addCardToList(Card card, UUID id){
        var result = cardService.addNewCard(card);
        if (!result.success) {
            return result.of(null);
        }
        return Result.SUCCESS.of(listRepository.findById(id)
                .map(l -> {
                    if(!l.cardList.contains(card)){
                        l.cardList.add(card);
                    }
                    return listRepository.save(l);
                }).get());
    }
}
