package client;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;


import client.scenes.MainCtrl;


public class MyModule implements Module {

    /** Prepares all classes for dependency injection */
    @Override
    public void configure(Binder binder) {
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
    }
}
