package game.lightmixdesign.com.myapplication.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import game.lightmixdesign.com.myapplication.Adapters.FriendAdapter;
import game.lightmixdesign.com.myapplication.Base.BaseFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.GsonHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.UserDataHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.DialogService;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.PlatformService;
import game.lightmixdesign.com.myapplication.R;
import game.lightmixdesign.com.myapplication.ViewModels.FriendsViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FriendsFragment extends BaseFragment<FriendsViewModel>
        implements AdapterClickListener<User>, View.OnClickListener {

    @BindView(R.id.name)
    AppCompatTextView userName;
    @BindView(R.id.age)
    AppCompatTextView age;
    @BindView(R.id.figure)
    AppCompatImageView figureImg;
    @BindView(R.id.circle)
    View status;
    @BindView(R.id.date_reg)
    AppCompatTextView date;
    @BindView(R.id.email)
    AppCompatTextView email;
    @BindView(R.id.phone)
    AppCompatTextView phone;
    @BindView(R.id.address)
    AppCompatTextView address;
    @BindView(R.id.geo_coordinates)
    AppCompatTextView geo;
    @BindView(R.id.about)
    AppCompatTextView about;
    @BindView(R.id.title_friend_list)
    AppCompatTextView title;
    @BindView(R.id.friend_list)
    RecyclerView friendsList;

    @Inject
    FriendsViewModel viewModel;
    private CustomProgressDialog progress;
    private CompositeDisposable disposables;

    @Override
    public FriendsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_friends;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        disposables = new CompositeDisposable();
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;

        String json = bundle.getString("user");
        if (json == null || json.isEmpty()) return;

        UserResponseActivityModel user = GsonHelper.getGson().fromJson(json, UserResponseActivityModel.class);
        viewModel.initViewModel(user);

        initClickListener();
        initDataFriends();
        setBinding();
        setBindingAction();
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

    private void initClickListener() {
        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        geo.setOnClickListener(this);
    }

    private void initDataFriends() {
        friendsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendsList.setAdapter(new FriendAdapter(viewModel, this, this));
        friendsList.setHasFixedSize(true);
    }

    @Override
    public void ItemClick(User item) {
        if (!item.isActive) return;

        viewModel.itemClick.onNext(item);

        UserResponseActivityModel user = UserDataHelper.convertUserToUserResponse(item);
        String json = GsonHelper.getGson().toJson(user);

        Intent intent = new Intent();
        intent.putExtra("user", json);

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(intent.getExtras());

        getBaseActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("FriendsFragment").commit();
    }

    @Override
    public void ElementItemClick(User item, int elementId) {

    }

    private void initFigure(UserResponseModel.Figure figure) {
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

    private void initStatusUser(UserResponseModel.ColorType color) {
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
                viewModel.callPhone();
                break;
            case R.id.email:
                viewModel.sendEmail();
                break;
            case R.id.geo_coordinates:
                viewModel.sendMapPoint();
                break;
        }
    }

    private void setBinding() {
        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null && isLoading) {
                progress = DialogService.ProgressDialog(getBaseActivity(), "Please wait");
            } else if (progress != null) {
                progress.dismiss();
            }
        });

        viewModel.getMessage().observe(this, observer -> {
            if (observer != null) {
                DialogService.MessageDialog(getBaseActivity(),
                        observer.first ? "Error" : "Successfully", observer.second, "OK", null);
            }
        });

        viewModel.getFruit().observe(this, fruit -> {
            if (fruit != null) {
                initFigure(fruit);
            }
        });

        viewModel.getStatus().observe(this, status -> {
            if (status != null) {
                initStatusUser(status);
            }
        });

        viewModel.getName().observe(this, name -> {
            if (name != null) {
                userName.setText(name);
            }
        });

        viewModel.getAge().observe(this, ageStr -> {
            if (ageStr != null) {
                age.setText(ageStr);
            }
        });

        viewModel.getDate().observe(this, dateStr -> {
            if (dateStr != null) {
                date.setText(dateStr);
            }
        });

        viewModel.getAddress().observe(this, addressStr -> {
            if (addressStr != null) {
                address.setText(addressStr);
            }
        });

        viewModel.getEmail().observe(this, emailStr -> {
            if (emailStr != null) {
                email.setText(emailStr);
            }
        });

        viewModel.getPhone().observe(this, phoneStr -> {
            if (phoneStr != null) {
                phone.setText(phoneStr);
            }
        });

        viewModel.getTitleFriend().observe(this, titleStr -> {
            if (titleStr != null) {
                title.setText(titleStr);
            }
        });

        viewModel.getAbout().observe(this, aboutStr -> {
            if (aboutStr != null) {
                about.setText(aboutStr);
            }
        });

        viewModel.getLocation().observe(this, locationStr -> {
            if (locationStr != null) {
                geo.setText(locationStr);
            }
        });
    }

    private void setBindingAction() {
        disposables.add(viewModel.callPhone.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        PlatformService.callPhone(getBaseActivity(), s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }

                    @Override
                    public void onComplete() {
                        /*ignore*/
                    }
                }));

        disposables.add(viewModel.sendEmail.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        PlatformService.sendEmail(getBaseActivity(), s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }

                    @Override
                    public void onComplete() {
                        /*ignore*/
                    }
                }));

        disposables.add(viewModel.sendMapPoint.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<android.util.Pair<Double, Double>>() {
                    @Override
                    public void accept(android.util.Pair<Double, Double> doubleDoublePair) throws Exception {
                        PlatformService.openMaps(getBaseActivity(), doubleDoublePair.first, doubleDoublePair.second);
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (disposables != null) {
            disposables.clear();
            disposables = null;
        }
    }
}