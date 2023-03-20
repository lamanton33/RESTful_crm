package client.utils;

import javafx.scene.Node;



public class MakeDraggable {

    private double mouseAnchorX;
    private double mouseAnchorY;

    /**
     * @param node node to be given draggability
     */
    public void makeDraggable(Node node){

        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }

}
