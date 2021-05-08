package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.Language;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.Theme;

public interface SettingsHelper {

    void saveHashTypeAsLast(@NonNull HashType hashType);

    @NonNull
    HashType getLastHashType();

    boolean languageIsInitialized();

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

}
