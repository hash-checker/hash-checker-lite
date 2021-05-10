package com.smlnskgmail.jaman.hashcheckerlite.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.MainActivity;

public class RestartUtils {

    private RestartUtils() {

    }

    public static void restartApp(
            @NonNull Activity activity
    ) {
        Intent intent = new Intent(
                activity,
                MainActivity.class
        );
        activity.startActivity(intent);
        activity.finish();
    }

}
