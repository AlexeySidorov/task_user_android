package game.lightmixdesign.com.myapplication.Infrastructure.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
}
