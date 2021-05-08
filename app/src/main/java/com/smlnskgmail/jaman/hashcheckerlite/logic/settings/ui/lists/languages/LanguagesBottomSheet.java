package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.languages;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.Language;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.Arrays;

import javax.inject.Inject;

public class LanguagesBottomSheet extends BaseListBottomSheet<Language> {

    @Inject
    LangHelper langHelper;

    @Inject
    ThemeHelper themeHelper;

    // CPD-OFF
    @Override
    public void onAttach(@NonNull Context context) {
        App.appComponent.inject(this);
        super.onAttach(context);
    }
    // CPD-ON

    @NonNull
    @Override
    protected BaseListAdapter<Language> getItemsAdapter() {
        return new LanguagesListAdapter(
                Arrays.asList(Language.values()),
                this,
                langHelper.currentLanguage(),
                langHelper,
                themeHelper
        );
    }

}
