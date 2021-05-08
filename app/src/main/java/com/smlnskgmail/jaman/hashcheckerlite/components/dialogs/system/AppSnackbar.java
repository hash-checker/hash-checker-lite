package com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

public class AppSnackbar {

    private static final int COMMON_SNACKBAR_MARGIN = 8;

    private final Context context;
    private final View parent;
    private final String message;
    private String actionText;
    private View.OnClickListener action;
    private final ThemeHelper themeHelper;

    public AppSnackbar(
            @NonNull Context context,
            @NonNull View parent,
            @NonNull String message,
            @NonNull String actionText,
            @NonNull View.OnClickListener action,
            @NonNull ThemeHelper themeHelper
    ) {
        this.context = context;
        this.parent = parent;
        this.message = message;
        this.actionText = actionText;
        this.action = action;
        this.themeHelper = themeHelper;
    }

    public AppSnackbar(
            @NonNull Context context,
            @NonNull View parent,
            @NonNull String message,
            @NonNull ThemeHelper themeHelper
    ) {
        this.context = context;
        this.parent = parent;
        this.message = message;
        this.themeHelper = themeHelper;
    }

    public void show() {
        Snackbar snackbar = Snackbar.make(
                parent,
                message,
                Snackbar.LENGTH_SHORT
        );
        if (action != null) {
            snackbar.setAction(actionText, action);
        } else {
            final Snackbar closableSnackbar = snackbar;
            snackbar.setAction(
                    context.getResources().getString(R.string.common_ok),
                    v -> closableSnackbar.dismiss()
            );
            ((ViewGroup) snackbar.getView()).getChildAt(0)
                    .setPadding(
                            COMMON_SNACKBAR_MARGIN,
                            COMMON_SNACKBAR_MARGIN,
                            COMMON_SNACKBAR_MARGIN,
                            COMMON_SNACKBAR_MARGIN
                    );
        }
        snackbar.setActionTextColor(
                themeHelper.getAccentColor()
        );
        snackbar.getView().setBackground(
                ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_snackbar
                )
        );

        TextView tvSnackbarText = snackbar.getView().findViewById(
                R.id.snackbar_text
        );
        tvSnackbarText.setTextColor(
                themeHelper.getCommonTextColor()
        );
        snackbar.show();
    }

}
