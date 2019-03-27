package game.lightmixdesign.com.myapplication.Infrastructure.Modules;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;
import game.lightmixdesign.com.myapplication.Base.ViewModelProviderFactory;
import game.lightmixdesign.com.myapplication.ViewModels.UsersViewModel;

@Module
public class UsersModule {
    @Provides
    UsersViewModel provideViewModel() {
        return new UsersViewModel();
    }

    @Provides
    ViewModelProvider.Factory provideViewModelProvider(UsersViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
