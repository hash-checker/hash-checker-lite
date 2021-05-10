package com.smlnskgmail.jaman.hashcheckerlite.di.components;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashchecker.di.scopes.ApplicationScope;
import com.smlnskgmail.jaman.hashcheckerlite.MainActivity;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.LangHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.SettingsHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.di.modules.ThemeHelperModule;
import com.smlnskgmail.jaman.hashcheckerlite.logic.feedback.FeedbackFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.HashCalculatorFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui.ActionSelectActionsBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui.SourceSelectActionsBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes.GenerateToBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.SettingsFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.languages.LanguagesBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.themes.ThemesBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks.AuthorWebLinksBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks.PrivacyPolicyWebLinksBottomSheet;

import dagger.Component;

@ApplicationScope
@Component(modules = {
        SettingsHelperModule.class,
        LangHelperModule.class,
        ThemeHelperModule.class,
})
public interface AppComponent {

    void inject(@NonNull MainActivity mainActivity);

    void inject(@NonNull HashCalculatorFragment hashCalculatorFragment);

    void inject(@NonNull FeedbackFragment feedbackFragment);

    void inject(@NonNull SettingsFragment settingsFragment);

    void inject(@NonNull GenerateToBottomSheet generateToBottomSheet);

    void inject(@NonNull ActionSelectActionsBottomSheet actionSelectActionsBottomSheet);

    void inject(@NonNull SourceSelectActionsBottomSheet sourceSelectActionsBottomSheet);

    void inject(@NonNull LanguagesBottomSheet languagesBottomSheet);

    void inject(@NonNull ThemesBottomSheet themesBottomSheet);

    void inject(@NonNull AuthorWebLinksBottomSheet authorWebLinksBottomSheet);

    void inject(@NonNull PrivacyPolicyWebLinksBottomSheet privacyPolicyWebLinksBottomSheet);

}
