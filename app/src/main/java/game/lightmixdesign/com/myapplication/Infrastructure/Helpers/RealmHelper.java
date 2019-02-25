package game.lightmixdesign.com.myapplication.Infrastructure.Helpers;

import io.realm.Realm;

public class RealmHelper {

    public static int getAutoIncrementId(Realm realm, Class<io.realm.RealmModel> className) {
        Number number = realm.where(className).max("id");
        if (number == null) return 1;
        else return (int) number + 1;
    }
}
