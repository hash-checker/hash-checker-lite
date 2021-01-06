package com.smlnskgmail.jaman.hashcheckerlite.components;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.utils.LangUtils;
import com.smlnskgmail.jaman.hashcheckerlite.utils.UIUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LangUtils.setLocale(
                this,
                SettingsHelper.getLanguage(this)
        );
        setTheme(UIUtils.getThemeResId(this));
        super.onCreate(savedInstanceState);
        create();
    }

    protected abstract void create();

}
