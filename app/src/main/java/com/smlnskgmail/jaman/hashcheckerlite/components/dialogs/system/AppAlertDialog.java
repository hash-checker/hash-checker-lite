package com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

public class AppAlertDialog {

    private final Context context;
    private final int titleResId;
    private final int messageResId;
    private final int positiveButtonTextResId;
    private final DialogInterface.OnClickListener positiveClickListener;
    private final ThemeHelper themeHelper;

    public AppAlertDialog(
            @NonNull Context context,
            int titleResId,
            int messageResId,
            int positiveButtonTextResId,
            @NonNull DialogInterface.OnClickListener positiveClickListener,
            @NonNull ThemeHelper themeHelper
    ) {
        this.context = context;
        this.titleResId = titleResId;
        this.messageResId = messageResId;
        this.positiveButtonTextResId = positiveButtonTextResId;
        this.positiveClickListener = positiveClickListener;
        this.themeHelper = themeHelper;
    }

    public void show() {
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AppAlertDialog)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(
                        positiveButtonTextResId,
                        positiveClickListener
                )
                .setNegativeButton(
                        R.string.common_cancel,
                        (dialog, which) -> dialog.cancel()
                )
                .create();
        alertDialog.setOnShowListener(dialog -> {
            AlertDialog currentDialog = ((AlertDialog) dialog);
            int textColor = themeHelper.getAccentColor();
            currentDialog.getButton(
                    DialogInterface.BUTTON_POSITIVE
            ).setTextColor(textColor);
            currentDialog.getButton(
                    DialogInterface.BUTTON_NEGATIVE
            ).setTextColor(textColor);
        });
        alertDialog.show();
    }

}
