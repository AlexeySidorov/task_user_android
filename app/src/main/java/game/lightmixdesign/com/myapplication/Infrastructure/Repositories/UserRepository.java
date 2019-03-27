package game.lightmixdesign.com.myapplication.Infrastructure.Repositories;

import java.util.ArrayList;

import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class UserRepository {
    private static UserRepository instance;
    Realm realm = Realm.getDefaultInstance();

    public static synchronized UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();

        return instance;
    }

    public Observable<Boolean> addOrUpdateUsers(ArrayList<User> users) {
        return Observable.create(observer -> {
            if (!observer.isDisposed()) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(users);
                realm.commitTransaction();
                observer.onNext(true);
            }
        });
    }

    public boolean isUsersEmpty() {
        return realm.where(User.class).count() == 0;
    }

    public ArrayList<User> getUsers() {
        RealmResults<User> users = realm.where(User.class).findAll();
        return new ArrayList<>(users);
    }

    public ArrayList<User> getUsersByIds(ArrayList<Integer> ids) {
        RealmResults<User> users = realm.where(User.class).in("id", ids.toArray(new Integer[0])).findAll();
        return new ArrayList<>(users);
    }
}