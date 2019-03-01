package game.lightmixdesign.com.myapplication.Holders;

import android.view.View;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.HolderClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private AppCompatTextView nameUser;
    private AppCompatTextView emailUser;
    private AppCompatImageView status;
    private View itemView;
    private HolderClickListener<User> listener;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemView = itemView;
        nameUser = itemView.findViewById(R.id.name_user);
        emailUser = itemView.findViewById(R.id.email_user);
        status = itemView.findViewById(R.id.icon_status_user);
    }

    public void BindData(User user) {
        nameUser.setText(user.name);
        emailUser.setText(user.email);
        Glide.with(itemView).
                load(user.isActive ? R.drawable.active_user : R.drawable.not_active_user).into(status);

        itemView.setTag(user);
        itemView.setOnClickListener(this);
    }

    public void setClickListener(HolderClickListener<User> listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.clickElement((User) itemView.getTag(), -1);
    }
}
