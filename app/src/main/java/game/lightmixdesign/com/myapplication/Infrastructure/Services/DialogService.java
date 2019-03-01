package game.lightmixdesign.com.myapplication.Infrastructure.Services;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import game.lightmixdesign.com.myapplication.Fragments.CustomMessageDialog;
import game.lightmixdesign.com.myapplication.Fragments.CustomProgressDialog;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.DialogCallback;
import game.lightmixdesign.com.myapplication.R;

public class DialogService {
    public static void MessageDialog(AppCompatActivity activity, String title, String text,
                                     String buttonName, DialogCallback<ButtonType> callback) {
        CustomMessageDialog fragment = new CustomMessageDialog();
        fragment.setView(R.layout.message_dialog_fragment, title, text, buttonName);
        fragment.setResult(callback);
        fragment.show(activity.getSupportFragmentManager(), "MessageDialog");
    }

    public static void MessageDialog(FragmentActivity activity, String title, String text,
                                     String buttonName, DialogCallback<ButtonType> callback) {
        CustomMessageDialog fragment = new CustomMessageDialog();
        fragment.setView(R.layout.message_dialog_fragment, title, text, buttonName);
        fragment.setResult(callback);
        fragment.show(activity.getSupportFragmentManager(), "MessageDialog");
    }

    public static void MessageDialog(Context context, String title, String text,
                                     String buttonName, DialogCallback<ButtonType> callback) {
        CustomMessageDialog fragment = new CustomMessageDialog();
        fragment.setView(R.layout.message_dialog_fragment, title, text, buttonName);
        fragment.setResult(callback);
        fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "MessageDialog");
    }

    public static CustomProgressDialog ProgressDialog(AppCompatActivity activity, String title) {
        CustomProgressDialog fragment = new CustomProgressDialog(activity);
        fragment.setView(R.layout.progress_dialog_fragment, title);
        fragment.show(activity.getSupportFragmentManager(), "CustomProgressDialog");
        return fragment;
    }

    public static CustomProgressDialog ProgressDialog(FragmentActivity activity, String title) {
        CustomProgressDialog fragment = new CustomProgressDialog(activity);
        fragment.setView(R.layout.progress_dialog_fragment, title);
        fragment.show(activity.getSupportFragmentManager(), "CustomProgressDialog");
        return fragment;
    }

    public static CustomProgressDialog ProgressDialog(Context ctx, String title) {
        AppCompatActivity activity = (AppCompatActivity) ctx;
        CustomProgressDialog fragment = new CustomProgressDialog(activity);
        fragment.setView(R.layout.progress_dialog_fragment, title);
        fragment.show(activity.getSupportFragmentManager(), "CustomProgressDialog");
        return fragment;
    }

    public enum ButtonType {
        FirstButton,
        SecondButton
    }
}