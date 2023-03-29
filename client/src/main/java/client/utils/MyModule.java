package client.utils;

import client.MultiboardCtrl;
import client.SceneCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import commons.utils.IDGenerator;
import commons.utils.RandomIDGenerator;

public class MyModule implements Module {

    /** Prepares all classes for dependency injection */
    @Override
    public void configure(Binder binder) {
        binder.bind(SceneCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ConnectionCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ServerUtils.class).in(Scopes.SINGLETON);
        binder.bind(IDGenerator.class).to(RandomIDGenerator.class).in(Scopes.SINGLETON);
        binder.bind(MultiboardCtrl.class).in(Scopes.SINGLETON);
    }
}
