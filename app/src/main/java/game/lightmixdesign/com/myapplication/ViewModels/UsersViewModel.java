package game.lightmixdesign.com.myapplication.ViewModels;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.UserDataHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.Repositories.UserRepository;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Rest.RestApi;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class UsersViewModel extends ViewModel {
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Pair<Boolean, String>> loadMessage = new MutableLiveData<>();
    public PublishSubject<User> itemClick = PublishSubject.create();
    private CompositeDisposable disposables;

    private UserRepository userRepository;

    {
        try {
            userRepository = UserRepository.class.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            loadMessage.setValue(new Pair<>(true, "User list is empty"));
        } catch (InstantiationException e) {
            e.printStackTrace();
            loadMessage.setValue(new Pair<>(true, "User list is empty"));
        }
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<Pair<Boolean, String>> getMessage() {
        return loadMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @Inject
    public UsersViewModel() {
        disposables = new CompositeDisposable();
        initData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposables != null) {
            disposables.clear();
            disposables = null;
        }
    }

    private void initData() {
        if (userRepository.isUsersEmpty())
            checkInternetConnection();
        else
            getUsersFromDAO();
    }

    private void getUsersFromDAO() {
        loading.setValue(true);
        users.setValue(userRepository.getUsers());
        loading.setValue(false);
    }

    private void checkInternetConnection() {
        disposables.add(RestApi.isConnection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean)
                            loadingUser();
                        else {
                            loadMessage.setValue(new Pair<>(true, "No internet connection"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadMessage.setValue(new Pair<>(true, "No internet connection"));
                    }

                    @Override
                    public void onComplete() {
                        loadMessage.setValue(new Pair<>(true, "No internet connection"));
                    }
                }));
    }

    private void loadingUser() {
        loading.setValue(true);

        disposables.add(RestApi.getUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<ArrayList<UserResponseModel>>() {
                            @Override
                            public void onNext(ArrayList<UserResponseModel> userResponseModels) {
                                if (userResponseModels.size() != 0) {
                                    loading.setValue(false);
                                    setUsers(UserDataHelper.convertUserResponseToUsers(userResponseModels));
                                } else {
                                    loading.setValue(false);
                                    loadMessage.setValue(new Pair<>(true, "User list is empty"));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                loadMessage.setValue(new Pair<>(true, "User list is empty"));
                            }

                            @Override
                            public void onComplete() {
                                /*ignored*/
                            }
                        }));

    }

    public void updateUsers() {
        checkInternetConnection();
    }

    private void setUsers(ArrayList<User> users) {
        disposables.add(userRepository.addOrUpdateUsers(users).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        getUsersFromDAO();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadMessage.setValue(new Pair<>(true, "User list is empty"));
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
