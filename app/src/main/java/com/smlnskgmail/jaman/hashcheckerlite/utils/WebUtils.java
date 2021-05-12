package com.smlnskgmail.jaman.hashcheckerlite.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system.AppSnackbar;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

public class WebUtils {

    private WebUtils() {

    }

    public static void openGooglePlay(
            @NonNull Context context,
            @NonNull View view,
            ThemeHelper themeHelper
    ) {
        final String appPackageName = context.getPackageName();
        Uri link;
        try {
            link = Uri.parse("market://details?id=" + appPackageName);
            context.startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            link
                    )
            );
        } catch (ActivityNotFoundException e) {
            try {
                link = Uri.parse(
                        "https://play.google.com/store/apps/details?id=" + appPackageName
                );
                context.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW,
                                link
                        )
                );
            } catch (ActivityNotFoundException e2) {

                new AppSnackbar(
                        context,
                        view,
                        context.getString(
                                R.string.message_error_start_google_play
                        ),
                        themeHelper

                ).show();

            }
        }
    }

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
            LogUtils.e(e);
        }
    }

}
