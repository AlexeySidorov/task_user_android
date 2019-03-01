package game.lightmixdesign.com.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import game.lightmixdesign.com.myapplication.Holders.UserViewHolder;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.HolderClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.R;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class UserAdapter extends RealmRecyclerViewAdapter<User, UserViewHolder> implements HolderClickListener<User> {
    private AdapterClickListener<User> listener;

    public UserAdapter(@Nullable OrderedRealmCollection<User> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = getItem(position);
        if (user != null)
            holder.BindData(user);

        holder.setClickListener(this);
    }

    public void setOnClickListener(AdapterClickListener<User> listener) {
        this.listener = listener;
    }

    @Override
    public void clickElement(User item, int elementId) {
        listener.ItemClick(item);
    }
}
