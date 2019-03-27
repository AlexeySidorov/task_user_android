package game.lightmixdesign.com.myapplication.BindingModules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import game.lightmixdesign.com.myapplication.Fragments.FriendsFragment;
import game.lightmixdesign.com.myapplication.Fragments.UsersFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Modules.FriendsModule;
import game.lightmixdesign.com.myapplication.Infrastructure.Modules.UsersModule;

@Module
abstract class FragmentsBindingModule {

    @ContributesAndroidInjector(modules = UsersModule.class)
    abstract UsersFragment provideUsersFragment();

    @ContributesAndroidInjector(modules = FriendsModule.class)
    abstract FriendsFragment provideFriendsFragment();
}