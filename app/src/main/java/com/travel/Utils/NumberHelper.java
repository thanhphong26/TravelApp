package com.travel.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class NumberHelper {

    public static String getFormattedPrice(float price) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}
