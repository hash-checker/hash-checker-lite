package com.smlnskgmail.jaman.hashcheckerlite;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
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
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.SettingsFragment;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    public static final String URI_FROM_EXTERNAL_APP
            = "com.smlnskgmail.jaman.hashcheckerlite.URI_FROM_EXTERNAL_APP";

    private static final int MENU_ITEM_SETTINGS = R.id.menu_main_section_settings;
    private static final int MENU_ITEM_FEEDBACK = R.id.menu_main_section_feedback;

    private static final int REQUEST_APP_UPDATE = 1;
    boolean doubleBackToExitPressedOnce = false;

    @Inject
    public SettingsHelper settingsHelper;

    @Inject
    public LangHelper langHelper;

    @Inject
    public ThemeHelper themeHelper;

    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.appComponent.inject(this);
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected SettingsHelper settingsHelper() {
        return settingsHelper;
    }

    @NonNull
    @Override
    protected LangHelper langHelper() {
        return langHelper;
    }

    @NonNull
    @Override
    protected ThemeHelper themeHelper() {
        return themeHelper;
    }

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
            settingsHelper.setGenerateFromShareIntentMode(true);
        } else if (externalFileUri != null) {
            mainFragment.setArguments(
                    getConfiguredBundleWithDataUri(
                            externalFileUri
                    )
            );
            settingsHelper.setGenerateFromShareIntentMode(true);
        } else if (intent != null) {
            mainFragment.setArguments(
                    getBundleForShortcutAction(
                            intent.getAction()
                    )
            );
            settingsHelper.setGenerateFromShareIntentMode(false);
        }
        showFragment(mainFragment);
        checkForUpdateAvailability();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
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
            waitForDoubleBackPressed();
        }
        for (Fragment fragmentInApp : getSupportFragmentManager().getFragments()) {
            if (fragmentInApp instanceof AppResumeTarget) {
                ((AppResumeTarget) fragmentInApp).appResume();
            }
        }

    }

    private void waitForDoubleBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,R.string.message_double_click, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void checkForUpdateAvailability() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                requestUpdate(appUpdateInfo);
            }
        });
    }

    private void requestUpdate(@NonNull AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    REQUEST_APP_UPDATE
            );
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content).getRootView(),
                getResources().getString(R.string.update_downloaded_message),
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction(
                getResources().getString(R.string.update_restart_action),
                view -> appUpdateManager.completeUpdate()
        );
        snackbar.show();
    }

}
