package com.smlnskgmail.jaman.hashcheckerlite;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smlnskgmail.jaman.hashcheckerlite.components.BaseActivity;
import com.smlnskgmail.jaman.hashcheckerlite.components.BaseFragment;
import com.smlnskgmail.jaman.hashcheckerlite.components.states.AppBackClickTarget;
import com.smlnskgmail.jaman.hashcheckerlite.components.states.AppResumeTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.feedback.FeedbackFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.HashCalculatorFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.SettingsFragment;

public class MainActivity extends BaseActivity {

    public static final String URI_FROM_EXTERNAL_APP
            = "com.smlnskgmail.jaman.hashchecker.URI_FROM_EXTERNAL_APP";

    @Override
    public void create() {
        Intent intent = getIntent();
        String scheme = null;
        ClipData clipData = null;
        if (intent != null) {
            scheme = intent.getScheme();
            clipData = intent.getClipData();
        }
        Uri externalFileUri = null;
        if (clipData != null) {
            externalFileUri = clipData.getItemAt(0).getUri();
        }

        HashCalculatorFragment mainFragment = new HashCalculatorFragment();
        if (scheme != null && (
                scheme.equals(ContentResolver.SCHEME_CONTENT) || scheme.equals(ContentResolver.SCHEME_FILE))) {
            mainFragment.setArguments(
                    getConfiguredBundleWithDataUri(
                            intent.getData()
                    )
            );
            SettingsHelper.setGenerateFromShareIntentMode(
                    this,
                    true
            );
        } else if (externalFileUri != null) {
            mainFragment.setArguments(
                    getConfiguredBundleWithDataUri(
                            externalFileUri
                    )
            );
            SettingsHelper.setGenerateFromShareIntentMode(
                    this,
                    true
            );
        } else {
            mainFragment.setArguments(
                    getBundleForShortcutAction(
                            intent.getAction()
                    )
            );
            SettingsHelper.setGenerateFromShareIntentMode(
                    this,
                    false
            );
        }

        showFragment(mainFragment);
    }

    private void showFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(
                        android.R.id.content,
                        fragment,
                        BaseFragment.CURRENT_FRAGMENT_TAG
                )
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                )
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    private Bundle getConfiguredBundleWithDataUri(@NonNull Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putString(
                URI_FROM_EXTERNAL_APP,
                uri.toString()
        );
        return bundle;
    }

    @NonNull
    private Bundle getBundleForShortcutAction(
            @Nullable String action
    ) {
        Bundle shortcutArguments = new Bundle();
        if (action != null && action.equals(App.ACTION_START_WITH_TEXT)) {
            shortcutArguments.putBoolean(
                    App.ACTION_START_WITH_TEXT,
                    true
            );
        } else if (action != null && action.equals(App.ACTION_START_WITH_FILE)) {
            shortcutArguments.putBoolean(
                    App.ACTION_START_WITH_FILE,
                    true
            );
        }
        return shortcutArguments;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        hideKeyboard();
        switch (item.getItemId()) {
            case R.id.menu_main_section_settings:
                showFragment(new SettingsFragment());
                break;
            case R.id.menu_main_section_feedback:
                showFragment(new FeedbackFragment());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                Activity.INPUT_METHOD_SERVICE
        );
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    findViewById(android.R.id.content).getWindowToken(),
                    0
            );
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                BaseFragment.CURRENT_FRAGMENT_TAG
        );
        if (fragment instanceof AppBackClickTarget) {
            ((AppBackClickTarget) fragment).appBackClick();
        }
        for (Fragment fragmentInApp : getSupportFragmentManager().getFragments()) {
            if (fragmentInApp instanceof AppResumeTarget) {
                ((AppResumeTarget) fragmentInApp).appResume();
            }
        }
    }

}
