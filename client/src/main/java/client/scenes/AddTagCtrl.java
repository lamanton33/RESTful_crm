package client.scenes;

import client.SceneCtrl;
import client.components.TagComponentCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Tag;
import commons.utils.IDGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class AddTagCtrl implements InstanceableComponent {

    private final ServerUtils server;
    private StompSession.Subscription subscription;
    private final SceneCtrl sceneCtrl;

    private final Card card;
    private final IDGenerator idGenerator;
    private UUID id;
    private Tag tag;
    private final List<TagComponentCtrl> tagComponentCtrls;
    private final MyFXML fxml;
    @FXML
    private TextField tagTitle;
    @FXML
    private ColorPicker backgroundColor;
    @FXML
    private ListView tagListBox;

    @Inject
    public AddTagCtrl(ServerUtils server, SceneCtrl sceneCtrl, Card card, IDGenerator idGenerator, MyFXML fxml) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.card = card;
        this.idGenerator = idGenerator;
        this.fxml = fxml;
        this.tagComponentCtrls = new ArrayList<>();
    }

    /**
     * Creates tag with given title and bg color
     */
    public void createTag() {
        String title = tagTitle.getText();
        String colour = backgroundColor.toString();
        if (this.tag != null) {
            var newTag = new Tag(this.tag.tagID, title, colour);
            newTag.cardId = id != null ? id : idGenerator.generateID();

            server.addTag(this.tag.tagID, newTag);
            addTagToUI(newTag);
        }


        sceneCtrl.showBoard();
    }

    /** Sets the details of a tag
     * @param tag
     * */
    public void setTag(Tag tag) {
        this.tag = tag;
        tagTitle.setText(tag.tagTitle);
        registerForMessages();
    }


    /**
     * Method to add tag to UI on the Tag menu screen.
     *
     * @param tag
     */
    private void addTagToUI(Tag tag) {
        var tagPair = fxml.load(TagComponentCtrl.class, "client", "scenes", "components", "TagComponent.fxml");
        tagListBox.getItems().add(tagPair.getValue());
        var ctrl = tagPair.getKey();

        tagComponentCtrls.add(ctrl);
        ctrl.setTag(tag);
    }



    @Override
    public void registerForMessages(){
        unregisterForMessages();
        System.out.println("Editing card:\t" + card.cardID + "\tregistered for messaging");
        subscription = server.registerForMessages("/topic/update-tag/", UUID.class, payload ->{
            System.out.println("Endpoint \"/topic/update-tag/\" has been hit by a card with the id:\t"
                    + payload);
            try {
                if(payload.equals(card.cardID)){
                    System.out.println("Refreshing edit view of tag:\t" + tag.tagID);
                    // Needed to prevent threading issues
                    Platform.runLater(this::refresh);
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void refresh() {
        System.out.println("Refreshing tags");
        var result = server.getCard(tag.tagID);
        System.out.println("result of tag refresh: " + result.message);
        if (!result.success) {
            if(result.code == 35) {         //Result.CARD_DOES_NOT_EXIST
                clearFields();
                sceneCtrl.showBoard();
                sceneCtrl.showError("Tag was deleted", "Failed to refresh tag");
                return;
            }

            sceneCtrl.showError(result.message, "Failed to refresh tag");
        }

        clearFields();
    }

    @Override
    public void unregisterForMessages() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }


    /**
     * Clears all fields
     */
    public void clearFields(){
        tagTitle.clear();
    }


}
