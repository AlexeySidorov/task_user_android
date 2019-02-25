package game.lightmixdesign.com.myapplication.Infrastructure.Rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import game.lightmixdesign.com.myapplication.Adapters.ImprovedDateTypeAdapter;
import game.lightmixdesign.com.myapplication.Infrastructure.ResponseModels.UserResponseModel;
import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RestApi {

    public static Observable<ArrayList<UserResponseModel>> getUsers() {
        return Observable.create(observer -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.dropbox.com/s/s8g63b149tnbg8x/users.json?raw=1").get().build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        observer.onError(new Throwable("User list is empty"));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.code() == 200) {
                            ResponseBody body = response.body();
                            if (body == null) {
                                observer.onError(new Throwable("User list is empty"));
                                return;
                            }

                            ArrayList users = getGson().fromJson(body.string(),
                                    new TypeToken<List<UserResponseModel>>() {
                                    }.getType());

                            observer.onNext(users);

                        } else {
                            observer.onError(new Throwable("User list is empty"));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                observer.onError(new Throwable("User list is empty"));
            }
        });
    }

    private static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
