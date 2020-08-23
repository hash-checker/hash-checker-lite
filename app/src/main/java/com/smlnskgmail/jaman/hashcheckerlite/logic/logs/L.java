package com.smlnskgmail.jaman.hashcheckerlite.logic.logs;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.BuildConfig;

public class L {

    public static void e(@NonNull Throwable t) {
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
    }

}
