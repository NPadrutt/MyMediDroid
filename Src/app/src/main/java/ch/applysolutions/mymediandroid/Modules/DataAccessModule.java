package ch.applysolutions.mymediandroid.Modules;

import android.app.Application;

import javax.inject.Singleton;

import ch.applysolutions.mymediandroid.App;
import ch.applysolutions.mymediandroid.MainActivity;
import ch.applysolutions.mymediandroid.ModificationsActivity;
import ch.applysolutions.mymediandroid.dataaccess.DataAccess.IntakeDataAccess;
import ch.applysolutions.mymediandroid.dataaccess.DataAccess.MedicineDataAccess;
import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
            App.class,
            MainActivity.class,
                ModificationsActivity.class
        },
        library = true,
        complete = false)

public class DataAccessModule {

    @Provides @Singleton public MedicineDataAccess provideMedicineDataAccess(Application app){
        return new MedicineDataAccess(app);
    }

    @Provides @Singleton public IntakeDataAccess provideIntakeDataAccess(Application app){
        return new IntakeDataAccess(app);
    }
}
