package game.lightmixdesign.com.myapplication.Base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.LinearLayout;

import java.util.Objects;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import game.lightmixdesign.com.myapplication.R;

public abstract class BaseFragment<T extends ViewModel> extends DaggerFragment {

    private T viewModel;
    private Unbinder unbinder;
    private AppCompatActivity activity;
    private Toolbar toolbar;

    public abstract T getViewModel();

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public AppCompatActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setBackgroundToolbar(int color) {
        Objects.requireNonNull(activity.getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(activity, color)));
    }

    @SuppressLint({"ResourceAsColor", "ObsoleteSdkInt"})
    public void setTitleToolbar(String title) {
        if (activity == null || toolbar == null) return;

        AppCompatTextView textView = new AppCompatTextView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        textView.setMaxLines(1);
        textView.setText(title);
        textView.setLayoutParams(params);
        textView.setTextSize(21);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        toolbar.addView(textView);
    }
}
