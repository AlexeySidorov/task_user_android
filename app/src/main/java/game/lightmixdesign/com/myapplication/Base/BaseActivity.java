package game.lightmixdesign.com.myapplication.Base;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import game.lightmixdesign.com.myapplication.R;
import androidx.appcompat.widget.Toolbar;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ActionBarDrawerToggle mDrawerToggle;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(this, R.color.transparent)));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.text_open, R.string.text_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(
                                INPUT_METHOD_SERVICE);
                boolean b = inputMethodManager.hideSoftInputFromWindow(
                        Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private boolean isMenuButton;

    public void setToolBarMenuButton(boolean isMenuButton) {
        this.isMenuButton = isMenuButton;
        initMainButtonToolBar();
    }

    private int color;

    //Цвет кнопки меню или кнопки назад
    public void setColorToolbarMainButton(int color) {
        this.color = color;
    }

    @SuppressLint("PrivateResource")
    @SuppressWarnings("ResourceAsColor")
    private void initMainButtonToolBar() {
        Drawable upArrow = null;
        if (!isMenuButton) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                upArrow = getDrawable(R.drawable.abc_ic_ab_back_material);
            else upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                assert upArrow != null;

                upArrow.setColorFilter(ContextCompat.getColor(getBaseContext(), color), PorterDuff.Mode.SRC_ATOP);
            } else {
                assert upArrow != null;
                upArrow.setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
            }

            mDrawerToggle.setHomeAsUpIndicator(upArrow);
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        }

        mDrawerToggle.syncState();
    }

    public void setHideMainButtonInToolbar() {
        mDrawerToggle.setHomeAsUpIndicator(null);
    }
}