package client;

import client.scenes.SceneCtrl;
import client.utils.*;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class MyModule implements Module {

    /** Prepares all classes for dependency injection */
    @Override
    public void configure(Binder binder) {
        binder.bind(SceneCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ConnectionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
    }
}
