package game.lightmixdesign.com.myapplication.Activites;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import game.lightmixdesign.com.myapplication.Base.BaseActivity;
import game.lightmixdesign.com.myapplication.Fragments.FriendsFragment;
import game.lightmixdesign.com.myapplication.R;

public class FriendsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayout(R.layout.activity_base);
        setOnlyMyViews(false);
        setToolBarMenuButton(false);
        setColorToolbarMainButton(R.color.white);
        super.onCreate(savedInstanceState);
        setTitle("Friends");
    }

    @Override
    protected void initViews2() {
        FragmentManager fr = getSupportFragmentManager();
        FriendsFragment friendsFragment = new FriendsFragment();
        friendsFragment.setArguments(getIntent().getExtras());
        fr.beginTransaction().add(R.id.container, friendsFragment, "FriendsFragment").commit();
    }
}
