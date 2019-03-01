package game.lightmixdesign.com.myapplication.Infrastructure.Helpers;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
    public static String formateDate(Date date, String format) {
        @SuppressLint("SimpleDateFormat")
        DateFormat pstFormat = new SimpleDateFormat(format);
        pstFormat.setTimeZone(TimeZone.getDefault());
        return pstFormat.format(date);
    }
}
