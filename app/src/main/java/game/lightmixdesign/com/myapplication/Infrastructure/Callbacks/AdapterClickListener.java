package game.lightmixdesign.com.myapplication.Infrastructure.Callbacks;

public interface AdapterClickListener<T> {
    void ItemClick(T item);
    void ElementItemClick(T item, int elementId);
}