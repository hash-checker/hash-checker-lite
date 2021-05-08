package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.languages;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListHolder;
import com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system.AppAlertDialog;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.Language;
import com.smlnskgmail.jaman.hashcheckerlite.logic.support.Restart;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.List;

public class LanguagesListAdapter extends BaseListAdapter<Language> {

    private final Language selectedLanguage;
    private final LangHelper langHelper;
    private final ThemeHelper themeHelper;

    LanguagesListAdapter(
            @NonNull List<Language> items,
            @NonNull BaseListBottomSheet<Language> bottomSheet,
            @NonNull Language selectedLanguage,
            @NonNull LangHelper langHelper,
            @NonNull ThemeHelper themeHelper
    ) {
        super(items, bottomSheet);
        this.selectedLanguage = selectedLanguage;
        this.langHelper = langHelper;
        this.themeHelper = themeHelper;
    }

    @NonNull
    @Override
    protected BaseListHolder<Language> getItemsHolder(
            @NonNull Context themeContext,
            @NonNull View view
    ) {
        return new LanguagesListHolder(
                themeContext,
                view
        );
    }

    private class LanguagesListHolder extends BaseListHolder<Language> {

        private Language languageAtPosition;

        LanguagesListHolder(
                @NonNull Context themeContext,
                @NonNull View itemView
        ) {
            super(themeContext, itemView, themeHelper);
        }

        @Override
        protected void callItemClick() {
            new AppAlertDialog(
                    getContext(),
                    R.string.title_warning_dialog,
                    R.string.message_change_language,
                    R.string.common_ok,
                    (dialog, which) -> {
                        langHelper.setLanguage(
                                languageAtPosition
                        );
                        dialog.dismiss();
                        Restart.restartApp(
                                getBottomSheet().getActivity()
                        );
                    },
                    themeHelper
            ).show();
        }

        @Override
        protected void bind(@NonNull Language listItem) {
            languageAtPosition = listItem;
            super.bind(listItem);
        }

        @Override
        protected boolean getConditionToAdditionalIconVisibleState() {
            return languageAtPosition == selectedLanguage;
        }

    }

}
