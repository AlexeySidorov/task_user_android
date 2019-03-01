package game.lightmixdesign.com.myapplication.Fragments;

import android.os.Bundle;
import android.view.View;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.DialogCallback;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.DialogService;
import game.lightmixdesign.com.myapplication.R;

public class CustomMessageDialog extends SupportBlurDialogFragment implements View.OnClickListener {

    private int resourceId;
    private String title;
    private String content;
    private String buttonName;
    private DialogCallback<DialogService.ButtonType> result;
    private AppCompatTextView titleDlg;
    private CircleImageView close;
    private AppCompatButton firstButton;
    private AppCompatTextView contentDlg;

    public void setView(int resourceId, String title, String content, String buttonName) {
        this.resourceId = resourceId;
        this.title = title;
        this.content = content;
        this.buttonName = buttonName;
    }

    public void setResult(DialogCallback<DialogService.ButtonType> result) {
        this.result = result;
    }

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.CustomDialog);
        View view = getActivity().getLayoutInflater().inflate(resourceId, null);
        initView(view);
        initClick();
        setContent();
        builder.setCancelable(false);
        builder.setView(view);
        this.setCancelable(false);
        return builder.create();
    }

    private void initView(View view) {
        titleDlg = view.findViewById(R.id.title_dialog);
        close = view.findViewById(R.id.close_dialog);
        firstButton = view.findViewById(R.id.first_dialog_button);
        contentDlg = view.findViewById(R.id.content);
    }

    private void initClick() {
        close.setOnClickListener(this);
        firstButton.setOnClickListener(this);
    }

    private void setContent() {
        titleDlg.setText(title);
        contentDlg.setText(content);
        firstButton.setText(buttonName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_dialog: {

                if (result != null)
                    result.onClose();

                dismiss();
                break;
            }
            case R.id.first_dialog_button: {

                if (result != null)
                    result.onResult(DialogService.ButtonType.FirstButton);

                dismiss();
                break;
            }
        }
    }
}
