package server.api.Card;

import commons.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import server.api.Task.*;
import server.database.*;

import java.util.*;

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
            var tasks = card.taskList;
            card.taskList = new ArrayList<>();
            cardRepository.save(card);
            for (var task :
                    tasks) {
                task.card = card;
                taskService.addNewTask(task);
            }
            card.taskList = tasks;
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
            return cardRepository.findById(id)
                    .map(c -> {
                        var tasks = new ArrayList<>(c.taskList);
                        c.taskList.removeIf(t -> true);
                        cardRepository.save(c);
                        tasks.forEach(task -> taskService.deleteTask(task.taskID));
                        cardRepository.deleteById(c.cardID);
                        return Result.SUCCESS.of(null);
                    }).get();
        }catch (Exception e){
            return Result.FAILED_DELETE_CARD;
        }
    }

    /**
     * Updates the name of the Card with specific id {id},
     * with the name of the given Card card.
     * @param card card with the new name
     * @param id id of the card to be updated
     */
    public Result<Object> updateCard(Card card, UUID id){

        try {
            return Result.SUCCESS.of(cardRepository.findById(id)
                    .map(l -> {
                        l = card;
                        for (var task :
                                card.taskList) {
                            task.card = card;
                            taskService.updateTask(task, task.taskID);
                        }
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
        if(id == null) return Result.OBJECT_ISNULL.of(null);
        try{
            if (!cardRepository.existsById(id)) {
                return Result.CARD_DOES_NOT_EXIST.of(null);
            }
            return Result.SUCCESS.of(cardRepository.findById(id).get());
        } catch (Exception e){
            return Result.FAILED_RETRIEVE_CARD_BY_ID;
        }
    }

    /**
     * Removes a certain task from the card with Id {id}
     */
    public Result<Card> removeTaskFromCard(Task task, UUID id){
        if(task == null || id == null) return Result.OBJECT_ISNULL.of(null);
        try{
            return Result.SUCCESS.of(cardRepository.findById(id)
                    .map(c -> {
                        c.taskList.remove(task);
                        cardRepository.save(c);
                        taskService.deleteTask(task.taskID);
                        return c;
                    }).get());
        } catch (Exception e){
            return Result.FAILED_REMOVE_CARD;
        }
    }

    /**
     * Adds the given task to the card with Id {id}
     */
    public Result<Card> addTaskToCard(Task task, UUID id){
//        Card card = cardRepository.findById(id).get();
//        task.card = card;
//        var result = taskService.addNewTask(task);
//        if(!result.success) {
//            return result.of(null);
//        }
        if(task == null || id == null) return Result.OBJECT_ISNULL.of(null);
        try{
            Card card = cardRepository.findById(id).get();
            task.card = card;
            taskService.addNewTask(task);
            card.taskList.add(task);
            cardRepository.save(card);
            return Result.SUCCESS.of(card);
        }
        catch (Exception e){
            return Result.FAILED_ADD_TASK_TO_CARD;
        }
    }

    /** Updates the card it receives in the database. */
    public Result<Card> updateCard(Card card) {
        try {
            return Result.SUCCESS.of(cardRepository.save(card));
        } catch (Exception e) {
            return Result.FAILED_UPDATE_CARD.of(null);
        }
    }
}
