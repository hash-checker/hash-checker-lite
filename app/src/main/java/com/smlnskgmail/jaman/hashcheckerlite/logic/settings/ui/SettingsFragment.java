package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragmentCompat;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.BuildConfig;
import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.states.AppBackClickTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.languages.LanguagesBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.themes.ThemesBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks.AuthorWebLinksBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks.PrivacyPolicyWebLinksBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;
import com.smlnskgmail.jaman.hashcheckerlite.utils.UIUtils;
import com.smlnskgmail.jaman.hashcheckerlite.utils.WebUtils;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements AppBackClickTarget {

    @Inject
    public ThemeHelper themeHelper;

    private ActionBar actionBar;
    private FragmentManager fragmentManager;
    private Context context;

    // CPD-OFF
    @Override
    public void onAttach(@NonNull Context context) {
        App.appComponent.inject(this);
        super.onAttach(context);
    }
    // CPD-ON

    @SuppressWarnings("MethodParametersAnnotationCheck")
    @SuppressLint("ResourceType")
    @Override
    public void onCreatePreferences(
            Bundle savedInstanceState,
            String rootKey
    ) {
        addPreferencesFromResource(R.xml.settings);
        fragmentManager = getActivity().getSupportFragmentManager();
        context = getContext();

        initializeActionBar();
        initializeLanguageSettings();
        initializeThemesSettings();
        initializePrivacyPolicy();
        initializeAuthorLinks();
        initializeRateButton();
        initializeHelpWithTranslationButton();
        initializeAppVersionInfo();
    }

    private void initializeActionBar() {
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(
                ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_arrow_back
                )
        );
    }

    private void initializeLanguageSettings() {
        findPreference(getString(R.string.key_language))
                .setOnPreferenceClickListener(preference -> {
                    LanguagesBottomSheet languagesBottomSheet = new LanguagesBottomSheet();
                    languagesBottomSheet.show(
                            fragmentManager,
                            languagesBottomSheet.getClass().getCanonicalName()
                    );
                    return false;
                });
    }

    private void initializeThemesSettings() {
        findPreference(getString(R.string.key_theme))
                .setOnPreferenceClickListener(preference -> {
                    ThemesBottomSheet themesBottomSheet = new ThemesBottomSheet();
                    themesBottomSheet.show(
                            fragmentManager,
                            themesBottomSheet.getClass().getCanonicalName()
                    );
                    return false;
                });
    }

    private void initializePrivacyPolicy() {
        findPreference(getString(R.string.key_privacy_policy))
                .setOnPreferenceClickListener(preference -> {
                    PrivacyPolicyWebLinksBottomSheet privacyPolicyWebLinksBottomSheet
                            = new PrivacyPolicyWebLinksBottomSheet();
                    privacyPolicyWebLinksBottomSheet.show(
                            fragmentManager,
                            privacyPolicyWebLinksBottomSheet.getClass().getCanonicalName()
                    );
                    return false;
                });
    }

    private void initializeAuthorLinks() {
        findPreference(getString(R.string.key_author))
                .setOnPreferenceClickListener(preference -> {
                    AuthorWebLinksBottomSheet authorWebLinksBottomSheet
                            = new AuthorWebLinksBottomSheet();
                    authorWebLinksBottomSheet.show(
                            fragmentManager,
                            authorWebLinksBottomSheet.getClass().getCanonicalName()
                    );
                    return false;
                });
    }

    private void initializeHelpWithTranslationButton() {
        findPreference(getString(R.string.key_help_with_translation))
                .setOnPreferenceClickListener(preference -> {
                    WebUtils.openWebLink(
                            context,
                            context.getString(R.string.web_link_help_with_translation)
                    );
                    return false;
                });
    }

    private void initializeRateButton() {
        findPreference(getString(R.string.key_rate_app))
                .setOnPreferenceClickListener(preference -> {
                    WebUtils.openGooglePlay(
                            context,
                            getView(),
                            themeHelper
                    );
                    return false;
                });
    }

    private void initializeAppVersionInfo() {
        findPreference(getString(R.string.key_version)).setSummary(
                String.format(
                        "%s (%s)",
                        BuildConfig.VERSION_NAME,
                        BuildConfig.VERSION_CODE
                )
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        view.setBackgroundColor(
                themeHelper.getCommonBackgroundColor()
        );
        setDividerHeight(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        actionBar.setTitle(R.string.menu_title_settings);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateOptionsMenu(
            @NonNull Menu menu,
            @NonNull MenuInflater inflater
    ) {
        menu.clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void appBackClick() {
        UIUtils.removeFragment(
                fragmentManager,
                this
        );
    }

}
