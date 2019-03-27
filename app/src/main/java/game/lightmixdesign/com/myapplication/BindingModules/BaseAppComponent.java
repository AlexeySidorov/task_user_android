package game.lightmixdesign.com.myapplication.BindingModules;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import game.lightmixdesign.com.myapplication.Base.BaseAppModule;
import game.lightmixdesign.com.myapplication.Task3App;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BaseAppModule.class,
        ActivityBindingModule.class
})
public interface BaseAppComponent extends AndroidInjector<Task3App> {
    @Override
    void inject(Task3App instance);
}