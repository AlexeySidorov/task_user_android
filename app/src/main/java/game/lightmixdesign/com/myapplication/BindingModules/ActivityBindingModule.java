package game.lightmixdesign.com.myapplication.BindingModules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import game.lightmixdesign.com.myapplication.Activites.MainActivity;

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = FragmentsBindingModule.class)
    abstract MainActivity contributeMainActivity();
}