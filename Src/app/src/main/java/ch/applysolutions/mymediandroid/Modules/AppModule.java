package ch.applysolutions.mymediandroid.Modules;

import android.app.Application;

import javax.inject.Singleton;

import ch.applysolutions.mymediandroid.App;
import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                App.class
        },
        library = true)

public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }


   @Provides @Singleton public Application provideApplication() {
        return app;
    }

}
