package client.interfaces;

import client.MyFXML;
import client.dataclass_controllers.Datasource;
import client.scenes.SceneCtrl;
import com.google.inject.Inject;

public interface UpdatableComponent {


    /**
     * Updates all the fields of the component
     */
    public void refresh();
}
