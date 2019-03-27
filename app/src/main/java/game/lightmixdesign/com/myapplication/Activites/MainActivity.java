package game.lightmixdesign.com.myapplication.Activites;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import game.lightmixdesign.com.myapplication.Base.BaseActivity;
import game.lightmixdesign.com.myapplication.Fragments.UsersFragment;
import game.lightmixdesign.com.myapplication.R;

@SuppressLint("Registered")
public class MainActivity extends BaseActivity {
    @Override
    protected int layoutRes() {
        return R.layout.activity_base;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setColorToolbarMainButton(R.color.white);
        setToolBarMenuButton(false);
        setHideMainButtonInToolbar();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.container, new UsersFragment()).commit();
    }
}
