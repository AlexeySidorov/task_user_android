package game.lightmixdesign.com.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import game.lightmixdesign.com.myapplication.Holders.UserViewHolder;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.HolderClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.R;
import game.lightmixdesign.com.myapplication.ViewModels.FriendsViewModel;

public class FriendAdapter extends RecyclerView.Adapter<UserViewHolder> implements HolderClickListener<User> {
    private AdapterClickListener<User> listener;
    private final List<User> data = new ArrayList<>();

    public FriendAdapter(FriendsViewModel viewModel, LifecycleOwner lifecycleOwner, AdapterClickListener<User> listener) {
        this.listener = listener;
        viewModel.getUsers().observe(lifecycleOwner, repos -> {
            data.clear();
            if (repos != null) {
                data.addAll(repos);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = data.get(position);
        if (user != null)
            holder.BindData(user);

        holder.setClickListener(this);
    }

    @Override
    public void clickElement(User item, int elementId) {
        listener.ItemClick(item);
    }
}
