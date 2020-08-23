package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.themes;

import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.SettingsHelper;

import java.util.Arrays;

public class ThemesBottomSheet extends BaseListBottomSheet<Theme> {

    @Override
    public BaseListAdapter<Theme> getItemsAdapter() {
        return new ThemesListAdapter(
                Arrays.asList(Theme.values()),
                this,
                SettingsHelper.getSelectedTheme(
                        getContext()
                )
        );
    }

}
