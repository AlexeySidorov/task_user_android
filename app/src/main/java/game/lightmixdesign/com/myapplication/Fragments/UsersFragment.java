package game.lightmixdesign.com.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import game.lightmixdesign.com.myapplication.Adapters.UserAdapter;
import game.lightmixdesign.com.myapplication.Base.BaseFragment;
import game.lightmixdesign.com.myapplication.Infrastructure.Callbacks.AdapterClickListener;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.GsonHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Helpers.UserDataHelper;
import game.lightmixdesign.com.myapplication.Infrastructure.Models.User;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseActivityModel;
import game.lightmixdesign.com.myapplication.Infrastructure.Services.DialogService;
import game.lightmixdesign.com.myapplication.R;
import game.lightmixdesign.com.myapplication.ViewModels.UsersViewModel;

public class UsersFragment extends BaseFragment<UsersViewModel> implements AdapterClickListener<User> {
    @BindView(R.id.users)
    RecyclerView recyclerView;

    @Inject
    UsersViewModel viewModel;
    private CustomProgressDialog progress;

    @Override
    public UsersViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_users;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        recyclerView.setAdapter(new UserAdapter(viewModel, this, this));
        recyclerView.setHasFixedSize(true);

        setTitleToolbar("Users");
        setHasOptionsMenu(true);
        setBinding();
    }

    @Override
    public void ItemClick(User item) {
        if(!item.isActive) return;

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_users: {
                viewModel.updateUsers();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
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
    }
}