package game.lightmixdesign.com.myapplication.Infrastructure.Modules;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;
import game.lightmixdesign.com.myapplication.Base.ViewModelProviderFactory;
import game.lightmixdesign.com.myapplication.ViewModels.FriendsViewModel;

@Module
public class FriendsModule {
    @Provides
    FriendsViewModel provideViewModel() {
        return new FriendsViewModel();
    }

    @Provides
    ViewModelProvider.Factory provideViewModelProvider(FriendsViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}