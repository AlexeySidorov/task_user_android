package game.lightmixdesign.com.myapplication.Base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<T extends ViewModel> extends DaggerFragment {

    private T viewModel;
    private Unbinder unbinder;
    private AppCompatActivity activity;

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
}

/*public class BaseFragment extends Fragment {
    private AppCompatActivity activity;
    private Toolbar toolbar;

    @SuppressLint("ResourceAsColor")
    public void setBackgroundToolbar(int color) {
        activity = ((AppCompatActivity) Objects.requireNonNull(getActivity()));
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setBackgroundDrawable(
                new ColorDrawable(ContextCompat.getColor(activity, color)));
    }

    @SuppressLint("ResourceAsColor")
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
}*/
