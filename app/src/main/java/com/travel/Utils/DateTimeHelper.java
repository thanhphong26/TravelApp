package com.travel.Utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateTimeHelper {
    public static Timestamp convertStringToTimeStamp(String dateTimeString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = inputFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return new Timestamp(System.currentTimeMillis());
    }
}
