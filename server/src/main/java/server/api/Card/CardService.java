package server.api.Card;

import commons.Result;
import commons.Card;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Task.TaskService;
import server.api.List.*;
import server.database.CardRepository;

import java.util.List;
import java.util.UUID;

/**
 * Handles logic of the Card endpoints
 */
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final TaskService taskService;

    @Autowired
    public CardService(CardRepository cardRepository, TaskService taskService) {
        this.cardRepository = cardRepository;
        this.taskService = taskService;
    }

    /**
     * Retrieves all the Cards from the repository
     */
    public Result<List<Card>> getAllCards(){
        try {
            return Result.SUCCESS.of(cardRepository.findAll());
        }catch (Exception e){
            return Result.FAILED_GET_ALL_CARDS;
        }
    }

    /**
     * Adds the Card to the repository
     * @param card
     */
    public Result<Card> addNewCard (Card card){
        if (card == null || card.cardTitle == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try {
            return Result.SUCCESS.of(cardRepository.save(card));
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_CARD.of(null);
        }
    }

    /**
     * Deletes the Card with the given id
     * @param id
     */
    public Result<Object> deleteCard (UUID id) {
        try {
            cardRepository.deleteById(id);
            return Result.SUCCESS;
        } catch (Exception e){
            return Result.FAILED_DELETE_CARD;
        }
    }

    /**
     * Updates the name of the Card with specific id {id},
     * with the name of the given Card card.
     * @param card
     * @param id
     */
    public Result<Object> updateName (Card card, UUID id){

        try {
            return Result.SUCCESS.of(cardRepository.findById(id)
                    .map(l -> {
                        l.setCardTitle(card.cardTitle);
                        return cardRepository.save(l);
                    }));
        }catch (Exception e){
            return Result.FAILED_UPDATE_CARD;
        }

    }

    /**
     * Get a card by an id method
     * @param id
     * @return card with specific ID
     */
    public Result<Card> getCardById(UUID id){
        try {
            return Result.SUCCESS.of(cardRepository.findById(id).get());
        }catch (Exception e){
            return Result.FAILED_RETRIEVE_CARD_BY_ID;
        }

    }

    /**
     * Removes a certain task from the card with Id {id}
     */
    public Result<Card> removeTaskFromCard(Task task, UUID id){
        //Remove task from repository
        try{
            taskService.deleteTask(task.taskID);
            return Result.SUCCESS.of(cardRepository.findById(id)
                    .map(c -> {
                        c.taskList.remove(task);
                        return cardRepository.save(c);
                    }).get());
        }
        catch (Exception e){
            return Result.FAILED_REMOVE_CARD;
        }
    }

    /**
     * Adds the given task to the card with Id {id}
     */
    public Result<Card> addTaskToCard(Task task, UUID id){
        var card = cardRepository.findById(id).get();
        task.card = card;
        var result = taskService.addNewTask(task);
        if(!result.success) {
            return result.of(null);
        }
        try{
            card.taskList.add(task);
            cardRepository.save(card);
            return Result.SUCCESS.of(card);
        }
        catch (Exception e){
            return Result.FAILED_ADD_TASK_TO_CARD;
        }
    }


}
