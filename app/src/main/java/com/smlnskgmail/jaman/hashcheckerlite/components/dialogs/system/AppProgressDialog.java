package com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

public class AppProgressDialog {

    private final Context context;
    private final int textMessageResId;
    private final ThemeHelper themeHelper;

    public AppProgressDialog(
            @NonNull Context context,
            int textMessageResId,
            @NonNull ThemeHelper themeHelper
    ) {
        this.context = context;
        this.textMessageResId = textMessageResId;
        this.themeHelper = themeHelper;
    }

    public ProgressDialog build() {
        android.app.ProgressDialog progressDialog
                = new android.app.ProgressDialog(context);
        progressDialog.setMessage(
                context.getString(
                        textMessageResId
                )
        );
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(
                        themeHelper.getCommonBackgroundColor()
                )
        );
        return progressDialog;
    }

}
