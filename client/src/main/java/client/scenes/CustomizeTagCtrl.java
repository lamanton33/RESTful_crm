package client.scenes;

import client.SceneCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomizeTagCtrl {

    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;

    private final Card card;

    @FXML
    private ColorPicker backgroundColor;

    @FXML
    private TextField tagTitle;

    private Tag tag;

    @Inject
    public CustomizeTagCtrl(ServerUtils server, SceneCtrl sceneCtrl, Card card) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.card = card;
    }

    /**
     * Initizalize the dynamic values to the desginated board
     */
    public void initialize(Tag tag){
        this.tag = tag;
        this.backgroundColor.setValue(Color.web(tag.tagColor));
        tagTitle.setText(tag.tagTitle);
    }

    /**
     * Retrieves the values for the new Theme, updates the board and returns to board overview.
     */
    public void save() {
        String title = tagTitle.getText();
        String colour = backgroundColor.toString();
        if (this.tag != null) {
            Tag newTag = new Tag(this.tag.tagID, title, colour);
            server.updateTag(this.tag.tagID, newTag);
        }

        //Should eventually return to board overview, not list overview
        sceneCtrl.showBoard();
    }


//    public void edit(Tag tag) {
//        created = true;
//        this.card = card;
//
//        titleOfTag.setText(tag.tagTitle);
//        description.setText(card.cardDescription);
//        for(var task : card.taskList) {
//            addTaskToUI(task.taskTitle, task.isCompleted);
//        }
//    }

    /**
     * Closes the customization operation, returns to the board overview scene
     */
    public void close() {
        sceneCtrl.showBoard();
    }

}

