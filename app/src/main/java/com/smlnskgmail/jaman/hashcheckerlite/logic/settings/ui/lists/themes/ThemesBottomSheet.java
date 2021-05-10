package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.themes;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.Theme;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.Arrays;

import javax.inject.Inject;

public class ThemesBottomSheet extends BaseListBottomSheet<Theme> {

    @Inject
    public SettingsHelper settingsHelper;

    @Inject
    public ThemeHelper themeHelper;

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        App.appComponent.inject(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public BaseListAdapter<Theme> getItemsAdapter() {
        return new ThemesListAdapter(
                Arrays.asList(Theme.values()),
                this,
                themeHelper
        );
    }

}
