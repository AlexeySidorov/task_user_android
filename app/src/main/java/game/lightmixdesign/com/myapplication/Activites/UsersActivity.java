package game.lightmixdesign.com.myapplication.Activites;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import game.lightmixdesign.com.myapplication.Base.BaseActivity;
import game.lightmixdesign.com.myapplication.Fragments.UsersFragment;
import game.lightmixdesign.com.myapplication.R;

public class UsersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayout(R.layout.activity_base);
        setOnlyMyViews(false);
        setToolBarMenuButton(false);
        setColorToolbarMainButton(R.color.white);
        super.onCreate(savedInstanceState);
        setTitle("Users");
    }

    @Override
    protected void initViews2() {
        FragmentManager fr = getSupportFragmentManager();
        UsersFragment usersFragment = new UsersFragment();
        fr.beginTransaction().add(R.id.container, usersFragment, "UsersFragment").commit();
    }
}
