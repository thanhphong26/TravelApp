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

    public static boolean checkIsValidDateTimeFormat(String format, String dateTimeString) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            sdf.parse(dateTimeString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String convertToDateFormat(String format, String date) {
        String[] dateFormats = {"dd/MM/yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "yyyy/MM/dd"};

        for (String df : dateFormats) {
            if (checkIsValidDateTimeFormat(df, date)) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat(df);
                    Date d = sdf.parse(date);
                    return new SimpleDateFormat(format).format(d);
                } catch (Exception e) {
                    return String.valueOf(date);
                }
            }
        }

        return String.valueOf(date);
    }

    public static String convertTimeStampToStringFormat(Timestamp timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(timestamp);
    }
    public static Date convertStringToTime(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
