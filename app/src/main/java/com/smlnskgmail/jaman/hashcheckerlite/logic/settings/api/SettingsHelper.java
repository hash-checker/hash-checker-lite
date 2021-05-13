package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.Language;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.Theme;

public interface SettingsHelper {

    int FILE_CREATE = 3;

    void saveHashTypeAsLast(@NonNull HashType hashType);

    @NonNull
    HashType getLastHashType();

    void saveLanguage(@NonNull Language language);

    @NonNull
    Language getLanguage();

    boolean isUsingMultilineHashFields();

    @NonNull
    Theme getSelectedTheme();

    void saveTheme(@NonNull Theme theme);

    boolean useUpperCase();

    boolean isShortcutsIsCreated();

    void saveShortcutsStatus(boolean value);

    boolean getGenerateFromShareIntentStatus();

    void setGenerateFromShareIntentMode(boolean status);

    boolean refreshSelectedFile();

    void setRefreshSelectedFileStatus(boolean status);

    boolean canShowRateAppDialog(Context context);

    void increaseHashGenerationCount(Context context);
}
