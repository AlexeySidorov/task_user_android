package game.lightmixdesign.com.myapplication.Infrastructure.Callbacks;

public interface HolderClickListener<T> {
    void clickElement(T item, int elementId);
}
