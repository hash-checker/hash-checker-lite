package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.languages;

import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.SettingsHelper;

import java.util.Arrays;

public class LanguagesBottomSheet extends BaseListBottomSheet<Language> {

    @Override
    protected BaseListAdapter<Language> getItemsAdapter() {
        return new LanguagesListAdapter(
                Arrays.asList(Language.values()),
                this,
                SettingsHelper.getLanguage(
                        getContext()
                )
        );
    }

}
