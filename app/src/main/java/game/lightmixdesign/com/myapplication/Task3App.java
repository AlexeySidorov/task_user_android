package game.lightmixdesign.com.myapplication;

import android.content.res.Configuration;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import game.lightmixdesign.com.myapplication.BindingModules.BaseAppComponent;
import game.lightmixdesign.com.myapplication.BindingModules.DaggerBaseAppComponent;
import io.realm.Realm;

import static game.lightmixdesign.com.myapplication.Infrastructure.Utils.SystemUtils.adjustFontScale;

public class Task3App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        BaseAppComponent component = DaggerBaseAppComponent.builder().build();
        component.inject(this);
        return component;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Configuration configuration = new Configuration(newConfig);
        adjustFontScale(getApplicationContext(), configuration);
    }
}