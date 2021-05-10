package com.smlnskgmail.jaman.hashcheckerlite.utils;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.BuildConfig;

public class LogUtils {

    private LogUtils() {

    }

    public static void e(@NonNull Throwable t) {
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
    }

}
