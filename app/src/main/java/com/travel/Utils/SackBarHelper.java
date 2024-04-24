package com.travel.Utils;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public final class SackBarHelper {

    @SuppressLint("ResourceType")
    public static void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundTintList(ColorStateList.valueOf(Color.rgb(204,153,255)));
        snackbar.show();
    }
}
