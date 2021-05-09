package com.smlnskgmail.jaman.hashcheckerlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.smlnskgmail.jaman.hashcheckerlite.di.components.AppComponent;
import com.smlnskgmail.jaman.hashcheckerlite.di.components.DaggerAppComponent;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.LangHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.SettingsHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.ThemeHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.impl.LangHelperImpl;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.impl.SharedPreferencesSettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.impl.ThemeHelperImpl;

import java.util.Arrays;

public class App extends android.app.Application {

    public static final String ACTION_START_WITH_TEXT
            = "com.smlnskgmail.jaman.hashcheckerlite.ACTION_START_WITH_TEXT";
    public static final String ACTION_START_WITH_FILE
            = "com.smlnskgmail.jaman.hashcheckerlite.ACTION_START_WITH_FILE";

    public static AppComponent appComponent;

    private static final String SHORTCUT_TEXT_ID = "shortcut_text";
    private static final String SHORTCUT_FILE_ID = "shortcut_file";

    private SettingsHelper settingsHelper;
    private LangHelper langHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        settingsHelper = new SharedPreferencesSettingsHelper(this);
        langHelper = new LangHelperImpl(
                this,
                settingsHelper
        );
        setTheme(settingsHelper.getSelectedTheme().getThemeResId());
        appComponent = DaggerAppComponent
                .builder()
                .settingsHelperModule(
                        new SettingsHelperModule(
                                settingsHelper
                        )
                )
                .langHelperModule(
                        new LangHelperModule(
                                langHelper
                        )
                )
                .themeHelperModule(
                        new ThemeHelperModule(
                                new ThemeHelperImpl(
                                        this,
                                        settingsHelper
                                )
                        )
                )
                .build();
        if (!settingsHelper.isShortcutsIsCreated()) {
            createShortcuts();
            settingsHelper.saveShortcutsStatus(true);
        }
        langHelper.applyLanguage(this);
    }

    private void createShortcuts() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = getSystemService(
                    ShortcutManager.class
            );
            if (shortcutManager != null) {
                shortcutManager.setDynamicShortcuts(
                        Arrays.asList(
                                getShortcutForTextType(),
                                getShortcutForFileType()
                        )
                );
            }
        }
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @SuppressLint("ResourceType")
    private ShortcutInfo getShortcutForTextType() {
        return getShortcut(
                SHORTCUT_TEXT_ID,
                R.string.common_text,
                R.drawable.ic_shortcut_text,
                ACTION_START_WITH_TEXT
        );
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @SuppressLint("ResourceType")
    private ShortcutInfo getShortcutForFileType() {
        return getShortcut(
                SHORTCUT_FILE_ID,
                R.string.common_file,
                R.drawable.ic_shortcut_file,
                ACTION_START_WITH_FILE
        );
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @SuppressLint("ResourceType")
    private ShortcutInfo getShortcut(
            @NonNull String id,
            @IdRes int labelResId,
            @IdRes int iconResId,
            @NonNull String intentAction
    ) {
        Intent intent = new Intent(
                this,
                MainActivity.class
        );
        intent.setAction(intentAction);
        return new ShortcutInfo.Builder(this, id)
                .setShortLabel(getString(labelResId))
                .setIcon(
                        Icon.createWithResource(
                                this,
                                iconResId
                        )
                )
                .setIntent(intent)
                .build();
    }

    @Override
    public void onConfigurationChanged(
            @NonNull Configuration newConfig
    ) {
        super.onConfigurationChanged(newConfig);
        langHelper.applyLanguage(this);
    }

}
