package game.lightmixdesign.com.myapplication.Infrastructure.Services;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.Locale;

import androidx.core.app.ActivityCompat;

public class PlatformService {

    public static void sendEmail(Context ctx, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        ctx.startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public static void callPhone(Context ctx, String phone) {
        if (ActivityCompat.checkSelfPermission(ctx,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            ctx.startActivity(intent);
        }
    }

    public static void openMaps(Context ctx, double latitude, double longitude) {
        String url = String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f (My marker)",
                latitude, longitude, latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        ctx.startActivity(intent);
    }
}
