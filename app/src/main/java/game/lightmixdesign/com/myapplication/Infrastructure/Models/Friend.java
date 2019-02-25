package game.lightmixdesign.com.myapplication.Infrastructure.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Friend extends RealmObject {
    @PrimaryKey
    private int id;
    private int userId;
    private int friendId;
}
