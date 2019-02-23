package game.lightmixdesign.com.myapplication.Base;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Objects;

import game.lightmixdesign.com.myapplication.R;


public class BaseFragment extends Fragment {
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
}
