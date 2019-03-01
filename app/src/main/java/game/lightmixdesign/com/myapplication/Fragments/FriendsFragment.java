package game.lightmixdesign.com.myapplication.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import game.lightmixdesign.com.myapplication.Activites.FriendsActivity;
import game.lightmixdesign.com.myapplication.Adapters.UserAdapter;
import game.lightmixdesign.com.myapplication.Base.BaseFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.DateHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.GsonHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.UserDataHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.Repositories.UserRepository;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.DialogService;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.PlatformService;
import game.lightmixdesign.com.myapplication.R;

import static game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel.*;

public class FriendsFragment extends BaseFragment implements AdapterClickListener<User>, View.OnClickListener {
    private AppCompatTextView userName;
    private AppCompatTextView age;
    private AppCompatImageView figureImg;
    private View status;
    private AppCompatTextView date;
    private AppCompatTextView email;
    private AppCompatTextView phone;
    private AppCompatTextView address;
    private AppCompatTextView geo;
    private AppCompatTextView about;
    private AppCompatTextView title;
    private RecyclerView friendsList;
    private UserResponseActivityModel user;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_friends, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initData();
    }

    private void initViews(View view) {
        userName = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        status = view.findViewById(R.id.circle);
        figureImg = view.findViewById(R.id.figure);
        date = view.findViewById(R.id.date_reg);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        geo = view.findViewById(R.id.geo_coordinates);
        about = view.findViewById(R.id.about);
        title = view.findViewById(R.id.title_friend_list);
        friendsList = view.findViewById(R.id.friend_list);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;

        String json = bundle.getString("user");
        if (json == null || json.isEmpty()) return;

        user = GsonHelper.getGson().fromJson(json, UserResponseActivityModel.class);
        initDataView();
    }

    @SuppressLint("SetTextI18n")
    private void initDataView() {
        userName.setText(user.name);
        age.setText(user.age.toString());
        date.setText(DateHelper.formateDate(user.registered, "HH:mm dd.MM.yy"));
        phone.setText(user.phone);
        email.setText(user.email);
        address.setText(user.address);
        geo.setText(user.latitude + ", " + user.longitude);
        about.setText(user.about);

        initClickListener();
        initStatusUser(user.eyeColor);
        initFigure(user.favoriteFruit);
        initDataFriends();
    }

    private void initClickListener() {
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        geo.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initDataFriends() {
        title.setText("Friends by" + user.name);

        UserAdapter adapter = new UserAdapter(UserRepository.getInstance().
                getUsersByIds(user.friends), true);
        adapter.setOnClickListener(this);
        friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendsList.setAdapter(adapter);
        friendsList.setHasFixedSize(true);
    }

    @Override
    public void ItemClick(User item) {
        if (item == null || !item.isActive) return;

        UserResponseActivityModel user = UserDataHelper.convertUserToUserResponse(item);
        String json = GsonHelper.getGson().toJson(user);

        Intent intent = new Intent(getActivity(), FriendsActivity.class);
        intent.putExtra("user", json);

        startActivity(intent);
    }

    @Override
    public void ElementItemClick(User item, int elementId) {

    }

    private void initFigure(Figure figure) {
        int resourceFigure = 0;

        switch (figure) {
            case apple: {
                resourceFigure = R.drawable.apple;
                break;
            }
            case banana: {
                resourceFigure = R.drawable.banane;
                break;
            }
            case strawberry: {
                resourceFigure = R.drawable.strawberry;
                break;
            }
        }

        Glide.with(this).load(resourceFigure).into(figureImg);
    }

    private void initStatusUser(ColorType color) {
        int resourceColor = 0;

        switch (color) {
            case blue: {
                resourceColor = R.color.blue;
                break;
            }
            case brown: {
                resourceColor = R.color.brown;
                break;
            }
            case green: {
                resourceColor = R.color.green;
                break;
            }
        }

        Drawable userStatusDrawable = AppCompatResources.getDrawable(Objects.requireNonNull(getActivity()),
                R.drawable.circle_drawable);

        if (userStatusDrawable != null) {
            userStatusDrawable.setColorFilter(ContextCompat.getColor(getActivity(), resourceColor)
                    , PorterDuff.Mode.SRC_ATOP);
            status.setBackground(userStatusDrawable);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone:
                PlatformService.callPhone(Objects.requireNonNull(getActivity()), user.phone);
                break;
            case R.id.email:
                PlatformService.sendEmail(Objects.requireNonNull(getActivity()), user.email);
                break;
            case R.id.geo_coordinates:
                PlatformService.openMaps(Objects.requireNonNull(getActivity()), user.latitude, user.longitude);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initPermission();
    }

    private void initPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }
}
