package ch.applysolutions.mymediandroid;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import ch.applysolutions.mymediandroid.Modules.AppModule;
import ch.applysolutions.mymediandroid.Modules.DataAccessModule;
import dagger.ObjectGraph;

public class App extends Application {
    private ObjectGraph objectGraph;

    protected List<Object> getModules() {
        return Arrays.asList(
                new AppModule(this),
                new DataAccessModule()
        );
    }

    private void createObjectGraphIfNeeded() {
        if (objectGraph == null) {
            Object[] modules = getModules().toArray();
            objectGraph = ObjectGraph.create(modules);
        }
    }

    public void inject(Object object) {
        createObjectGraphIfNeeded();
        objectGraph.inject(object);
    }
}
