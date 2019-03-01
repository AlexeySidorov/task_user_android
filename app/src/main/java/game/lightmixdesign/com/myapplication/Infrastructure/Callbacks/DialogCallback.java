package game.lightmixdesign.com.myapplication.Infrastructure.Callbacks;

public interface DialogCallback<T> {
    void onResult(T result);

    void onClose();
}