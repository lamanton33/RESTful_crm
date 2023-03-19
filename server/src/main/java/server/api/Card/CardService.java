package server.api.Card;

import commons.Card;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.Task.TaskService;
import server.database.CardRepository;

import java.util.List;

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
    public Card updateCardName (Card card, Integer id){
        return cardRepository.findById(id)
                .map(l -> {
                    l.setCardTitle(card.cardTitle);
                    return cardRepository.save(l);
                }).get();
    }

    /**
     * Get a card by an id method
     * @param id
     * @return card with specific ID
     */
    public Card getCardById(Integer id){
        return cardRepository.findById(id).get();
    }

    /**
     * Removes a certain task from the card with Id {id}
     */
    public Card removeTaskFromCard(Task task, Integer id){
        //Remove task from repository
        taskService.deleteTask(task.taskID);
        return cardRepository.findById(id)
                .map(c -> {
                    c.taskList.remove(task);
                    return cardRepository.save(c);
                }).get();
    }

    /**
     * Adds the given task to the card with Id {id}
     */
    public Card addTaskToCard(Task task, Integer id){
        taskService.addNewTask(task);
        return cardRepository.findById(id)
                .map(c -> {
                    c.taskList.add(task);
                    return cardRepository.save(c);
                }).get();
    }


}
