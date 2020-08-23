package com.smlnskgmail.jaman.hashcheckerlite.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.logs.L;

public class WebUtils {

    public static void openWebLink(
            @NonNull Context context,
            @NonNull String link
    ) {
        try {
            context.startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)
                    )
            );
        } catch (ActivityNotFoundException e) {
            L.e(e);
        }
    }

}
