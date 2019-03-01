package game.lightmixdesign.com.myapplication.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import game.lightmixdesign.com.myapplication.Activites.FriendsActivity;
import game.lightmixdesign.com.myapplication.Adapters.UserAdapter;
import game.lightmixdesign.com.myapplication.Base.BaseFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.GsonHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.UserDataHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.Repositories.UserRepository;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Rest.RestApi;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.DialogService;
import game.lightmixdesign.com.myapplication.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UsersFragment extends BaseFragment implements AdapterClickListener<User> {
    private CompositeDisposable disposables;
    private RecyclerView recyclerView;
    private CustomProgressDialog progress;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_users, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        setHasOptionsMenu(true);
        disposables = new CompositeDisposable();
        initDate();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.users);
    }

    private void initDate() {
        progress = DialogService.ProgressDialog(getActivity(), "Please wait");

        if (UserRepository.getInstance().isUsersEmpty())
            checkInternetConnection();
        else
            initDataToView();
    }

    private void checkInternetConnection() {
        disposables.add(RestApi.isConnection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Boolean>() {

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean)
                            initRestDate();
                        else {
                            progress.dismiss();
                            DialogService.MessageDialog(Objects.requireNonNull(getActivity()), "Internet connection",
                                    "No internet connection", "OK", null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        /*ignored*/
                    }

                    @Override
                    public void onComplete() {
                        /*ignored*/
                    }
                }));
    }

    private void initRestDate() {
        disposables.add(RestApi.getUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<ArrayList<UserResponseModel>>() {
                            @Override
                            public void onNext(ArrayList<UserResponseModel> userResponseModels) {
                                if (userResponseModels.size() != 0) {
                                    ArrayList<User> users = UserDataHelper.convertUserResponseToUsers(userResponseModels);
                                    disposables.add(UserRepository.getInstance().addOrUpdateUsers(users)
                                            .subscribe(aBoolean -> initDataToView()));
                                } else {
                                    progress.dismiss();
                                    DialogService.MessageDialog(Objects.requireNonNull(getActivity()), "User list",
                                            "User list is empty", "OK", null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                progress.dismiss();
                                DialogService.MessageDialog(Objects.requireNonNull(getActivity()), "User list",
                                        e.getMessage(), "OK", null);
                            }

                            @Override
                            public void onComplete() {
                                /*ignored*/
                            }
                        }));
    }

    private void initDataToView() {
        if (disposables.size() > 0)
            disposables.dispose();

        UserAdapter adapter = new UserAdapter(UserRepository.getInstance().getUsers(), true);
        adapter.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        progress.dismiss();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_users: {
                progress = DialogService.ProgressDialog(getActivity(), "Please wait");
                checkInternetConnection();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
