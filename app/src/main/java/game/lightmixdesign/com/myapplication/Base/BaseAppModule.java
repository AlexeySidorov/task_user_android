package game.lightmixdesign.com.myapplication.Base;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import game.lightmixdesign.com.myapplication.Task3App;

@Module
public class BaseAppModule
{
    private Task3App application;

    public BaseAppModule(Task3App app)
    {
        application = app;
    }

    @Provides
    Application provideApplication()
    {
        return application;
    }
}