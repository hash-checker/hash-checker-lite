package com.smlnskgmail.jaman.hashcheckerlite;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
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
            = "com.smlnskgmail.jaman.hashcheckerlite.URI_FROM_EXTERNAL_APP";
  
    private static final int MENU_ITEM_SETTINGS = R.id.menu_main_section_settings;
    private static final int MENU_ITEM_FEEDBACK = R.id.menu_main_section_feedback;

    private static final int REQUEST_APP_UPDATE = 1;
    private AppUpdateManager appUpdateManager;

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
        } else if (intent != null) {
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

        checkForUpdateAvailability();
    }

    // Checks that the update is not stalled during onResume().
    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    // If the update is downloaded but not installed,
                    // notify the user to complete the update.
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate();
                    }
                });
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
            case MENU_ITEM_SETTINGS:
                showFragment(new SettingsFragment());
                break;
            case MENU_ITEM_FEEDBACK:
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

    private void checkForUpdateAvailability() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Request the update.
                requestUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE, this, REQUEST_APP_UPDATE);
            }
        });
    }

    private void requestUpdate(AppUpdateInfo appUpdateInfo, int updateType, Context context, int requestCode) {

        // monitor the state of an update
        InstallStateUpdatedListener listener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a snackbar
                // and request user confirmation to restart the app.
                popupSnackbarForCompleteUpdate();
            }
        };

        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    updateType,
                    (Activity) context,
                    requestCode);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    // Displays the snackbar notification and call to action.
    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content).getRootView(),
                        getResources().getString(R.string.update_downloaded_message),
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getResources().getString(R.string.update_restart_action),
                view -> appUpdateManager.completeUpdate());
        snackbar.show();
    }

}
