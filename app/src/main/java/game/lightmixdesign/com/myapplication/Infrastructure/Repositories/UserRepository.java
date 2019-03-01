package game.lightmixdesign.com.myapplication.Infrastructure.Repositories;

import java.util.ArrayList;

import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import io.reactivex.Observable;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

public class UserRepository {
    private static UserRepository instance;
    private Realm realm = Realm.getDefaultInstance();

    public static synchronized UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();

        return instance;
    }

    public Observable<Boolean> addOrUpdateUsers(ArrayList<User> users) {
        return Observable.create(observer -> {
            realm.beginTransaction();
            realm.insertOrUpdate(users);
            realm.commitTransaction();
            observer.onNext(true);
        });
    }

    public boolean isUsersEmpty() {
        return realm.where(User.class).count() == 0;
    }

    public OrderedRealmCollection<User> getUsers() {
        return realm.where(User.class).findAll();
    }

    public OrderedRealmCollection<User> getUsersByIds(ArrayList<Integer> ids) {
        return realm.where(User.class).in("id", ids.toArray(new Integer[0])).findAll();
    }
}