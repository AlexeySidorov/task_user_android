package game.lightmixdesign.com.myapplication.Holders;

import android.view.View;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.HolderClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View itemView;
    private HolderClickListener<User> listener;

    @BindView(R.id.name_user)
    AppCompatTextView nameUser;
    @BindView(R.id.email_user)
    AppCompatTextView emailUser;
    @BindView(R.id.icon_status_user)
    AppCompatImageView status;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
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