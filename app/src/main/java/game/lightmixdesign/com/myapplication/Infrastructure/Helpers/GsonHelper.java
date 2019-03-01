package game.lightmixdesign.com.myapplication.Infrastructure.Helpers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import game.lightmixdesign.com.myapplication.Adapters.ImprovedDateTypeAdapter;

public class GsonHelper {
    public static Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
