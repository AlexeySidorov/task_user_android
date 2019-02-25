package game.lightmixdesign.com.myapplication.Base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import game.lightmixdesign.com.myapplication.R;
import game.lightmixdesign.com.myapplication.Task3App;
import game.lightmixdesign.com.myapplication.Utils.SystemUtils;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public int selfMenuItem = -1;
    boolean onlyMyViews = false;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private NavigationView mNavigationView;
    private CoordinatorLayout coordinatorLayout;
    private int layout = R.layout.activity_base;
    private Task3App myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        myApp = (Task3App) this.getApplicationContext();
        initViews();
    }

    public void setOnlyMyViews(boolean onlyMyViews) {
        this.onlyMyViews = onlyMyViews;
    }

    //Workaround =)
    @SuppressWarnings("deprecation")
    public void initViews() {
        if (!onlyMyViews) {
            mDrawerLayout = findViewById(R.id.drawer_layout);
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
                    inputMethodManager.hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), 0);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            initMainButtonToolBar();
        }

        initViews2();
    }

    @SuppressLint("SetTextI18n")
    public void menuUpdate() {
        View headerLayout = mNavigationView.getHeaderView(0);
    }

    private boolean isMenuButton;

    //Кнопка меню или кнопка назад
    public void setToolBarMenuButton(boolean isMenuButton) {
        this.isMenuButton = isMenuButton;
    }

    private int colorText;

    //Цвет заголовка активити
    public void setColorToolbarTitle(int color) {
        toolbar.setTitleTextColor(color);
        toolbar.setSubtitleTextColor(color);
    }

    private int color;

    //Цвет кнопки меню или кнопки назад
    public void setColorToolbarMainButton(int color) {
        this.color = color;
    }

    public void setToolbarBackground(int color) {
        if (color == 0) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.transparent)));
        } else
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, color)));
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

    // Свои вьюхи
    protected abstract void initViews2();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!onlyMyViews)
            mDrawerToggle.syncState();
    }

    //Задает id пункта меню для данной Activity
    public void setSelfMenuItem(int selfMenuItem) {
        this.selfMenuItem = selfMenuItem;
    }

    //Задает заголовок тулбара
    @Override
    public void setTitle(int id) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(id);
    }

    public void setImageTitle(int resourceId, boolean isCenter) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(resourceId, null);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,
                Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = isCenter ? Gravity.CENTER : Gravity.START | Gravity.CENTER_VERTICAL;
        toolbar.addView(mCustomView, params);
    }

    //Задает ресурс макета
    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!onlyMyViews) {
            if (selfMenuItem != -1)
                mNavigationView.setCheckedItem(selfMenuItem);
        }

        myApp.setCurrentActivity(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Menu menuNav = mNavigationView.getMenu();

        return true;
    }

    /// <summary>
    /// Скрыть клавиатуру
    /// </summary>
    /// <param name="view"></param>
    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void setHideMainButtonInToolbar() {
        mDrawerToggle.setHomeAsUpIndicator(null);
    }

    public void removeToolbarBottomLine() {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
    }

    public void setVisibleNavigationButton() {
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(null);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    private int ScreenId = -1;

    public void setTypeScreen(int screenId) {
        ScreenId = screenId;
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences() {
        Activity currActivity = myApp.getCurrentActivity();
        if (this.equals(currActivity))
            myApp.setCurrentActivity(null);
    }
}
