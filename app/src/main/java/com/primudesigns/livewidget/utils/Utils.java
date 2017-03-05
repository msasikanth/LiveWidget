package com.primudesigns.livewidget.utils;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.view.View;

public class Utils {

    public static void showSnackBar(Activity activity, int view, String text, int length) {
        if (activity != null) {
            View layout = activity.findViewById(view);
            Snackbar snackbar = Snackbar.make(layout, text, length);
            snackbar.show();
        }
    }

}
