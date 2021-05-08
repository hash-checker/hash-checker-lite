package com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api;

import android.content.Context;

import androidx.annotation.NonNull;

public interface LangHelper {

    void setLanguage(@NonNull Language language);

    void applyLanguage(@NonNull Context context);

    @NonNull
    Language currentLanguage();

}
