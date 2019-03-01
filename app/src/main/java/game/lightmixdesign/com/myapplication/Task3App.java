package game.lightmixdesign.com.myapplication;

import android.app.Application;
import android.content.res.Configuration;

import game.lightmixdesign.com.myapplication.Base.BaseActivity;
import io.realm.Realm;

import static game.lightmixdesign.com.myapplication.Infrastructure.Utils.SystemUtils.adjustFontScale;


public class Task3App extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Realm.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private static BaseActivity mCurrentActivity = null;

    public static BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(BaseActivity mCurrentActivity) {
        Task3App.mCurrentActivity = mCurrentActivity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Configuration configuration = new Configuration(newConfig);
        adjustFontScale(getApplicationContext(), configuration);
    }
}