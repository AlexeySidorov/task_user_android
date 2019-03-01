package game.lightmixdesign.com.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatTextView;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import game.lightmixdesign.com.myapplication.R;

public class CustomProgressDialog extends SupportBlurDialogFragment {

    private int resourceId;
    private String title;
    private AppCompatTextView titleDlg;
    private Context ctx;
    private AlertDialog.Builder builder;

    public CustomProgressDialog() {

    }

    @SuppressLint("ValidFragment")
    public CustomProgressDialog(Context context) {
        ctx = context;
    }

    public void setView(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public void ChangeTitle(String title) {
        titleDlg.setText(title);
    }

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(ctx, R.style.CustomDialog);
        View view = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(resourceId, null);
        initView(view);
        builder.setCancelable(false);
        builder.setView(view);
        this.setCancelable(false);
        return builder.create();
    }

    private void initView(View view) {
        titleDlg = view.findViewById(R.id.title_dialog);
    }
}
