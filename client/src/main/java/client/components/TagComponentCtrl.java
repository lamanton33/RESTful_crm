package client.components;

import client.SceneCtrl;
import client.utils.ServerUtils;
import commons.Tag;
import commons.utils.IDGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.UUID;

public class TagComponentCtrl {
    private final SceneCtrl sceneCtrl;
    private final IDGenerator idGenerator;
    private final ServerUtils server;
    private UUID id;
    private Tag tag;
    @FXML
    private TextField tagLabel;


    @Inject
    public TagComponentCtrl(SceneCtrl sceneCtrl, IDGenerator idGenerator, ServerUtils server){
        this.sceneCtrl = sceneCtrl;
        this.idGenerator = idGenerator;
        this.server = server;
        this.id = id;
        this.tagLabel = tagLabel;
    }

    /** Sets the UI components to the specified task */
    public void setTag(String title) {
        tagLabel.setText(title);
    }

    public Tag getTag() {
        String title = tagLabel.getText();
        String colour = null;
        Tag tagForReturn = null;
        if (this.tag != null) {
            var newTag = new Tag(this.tag.tagID, title, colour);
            newTag.cardId = id != null ? id : idGenerator.generateID();
            tagForReturn= newTag;
        }




        return tagForReturn;
    }
}
