package game.lightmixdesign.com.myapplication.ViewModels;

import android.util.Pair;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.DateHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.Repositories.UserRepository;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import io.reactivex.subjects.PublishSubject;

public class FriendsViewModel extends ViewModel {
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<UserResponseModel.Figure> fruit = new MutableLiveData<>();
    private final MutableLiveData<UserResponseModel.ColorType> status = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Pair<Boolean, String>> loadMessage = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> age = new MutableLiveData<>();
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> date = new MutableLiveData<>();
    private final MutableLiveData<String> address = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>();
    private final MutableLiveData<String> titleFriend = new MutableLiveData<>();
    private final MutableLiveData<String> phone = new MutableLiveData<>();
    private final MutableLiveData<String> about = new MutableLiveData<>();
    public PublishSubject<String> callPhone = PublishSubject.create();
    public PublishSubject<String> sendEmail = PublishSubject.create();
    public PublishSubject<Pair<Double, Double>> sendMapPoint = PublishSubject.create();
    public PublishSubject<User> itemClick = PublishSubject.create();
    private UserRepository userRepository;
    private UserResponseActivityModel user;

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

    public LiveData<UserResponseModel.Figure> getFruit() {
        return fruit;
    }

    public LiveData<UserResponseModel.ColorType> getStatus() {
        return status;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getAge() {
        return age;
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getDate() {
        return date;
    }

    public LiveData<String> getAddress() {
        return address;
    }

    public LiveData<String> getLocation() {
        return location;
    }

    public LiveData<String> getTitleFriend() {
        return titleFriend;
    }

    public LiveData<String> getPhone() {
        return phone;
    }

    public LiveData<String> getAbout() {
        return about;
    }

    public void initViewModel(UserResponseActivityModel user) {
        this.user = user;
        getUsersFromDAO(user);
        setBinding();
    }

    @Inject
    public FriendsViewModel() {

    }

    private void getUsersFromDAO(UserResponseActivityModel model) {
        loading.setValue(true);
        users.setValue(userRepository.getUsersByIds(model.friends));
        loading.setValue(false);
    }

    public void callPhone() {
        callPhone.onNext(user.phone);
    }

    public void sendEmail() {
        sendEmail.onNext(user.email);
    }

    public void sendMapPoint() {
        sendMapPoint.onNext(new Pair<>(user.latitude, user.longitude));
    }

    private void setBinding() {
        fruit.setValue(user.favoriteFruit);
        status.setValue(user.eyeColor);
        email.setValue(user.email);
        phone.setValue(user.phone);
        name.setValue(user.name);
        age.setValue(user.age.toString());
        about.setValue(user.about);
        address.setValue(user.address);
        location.setValue(user.latitude + ", " + user.longitude);
        date.setValue(DateHelper.formateDate(user.registered, "HH:mm dd.MM.yy"));
        titleFriend.setValue("Friends by" + user.name);
    }
}