package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.MainActivity;
import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.BaseFragment;
import com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system.AppProgressDialog;
import com.smlnskgmail.jaman.hashcheckerlite.components.dialogs.system.AppSnackbar;
import com.smlnskgmail.jaman.hashcheckerlite.components.watchers.AppTextWatcher;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashCalculatorTask;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.input.TextInputDialog;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.input.TextValueTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.types.UserActionTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.types.UserActionType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui.ActionSelectActionsBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui.SourceSelectActionsBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes.GenerateToBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes.HashTypeSelectTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.locale.api.LangHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.support.Clipboard;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;
import com.smlnskgmail.jaman.hashcheckerlite.utils.LogUtils;

import java.io.File;

import javax.inject.Inject;

public class HashCalculatorFragment extends BaseFragment
        implements TextValueTarget, UserActionTarget, HashTypeSelectTarget {

    private static final int FILE_SELECT = 101;
    private boolean doubleBackToExitPressedTwice = false;

    private static final int TEXT_MULTILINE_LINES_COUNT = 3;
    private static final int TEXT_SINGLE_LINE_LINES_COUNT = 1;

    @Inject
    public SettingsHelper settingsHelper;

    @Inject
    public LangHelper langHelper;

    @Inject
    public ThemeHelper themeHelper;

    private View mainScreen;

    private EditText etCustomHash;
    private EditText etGeneratedHash;

    private TextView tvSelectedObjectName;
    private TextView tvSelectedHashType;

    private Button btnGenerateFrom;

    private ProgressDialog progressDialog;

    private Uri fileUri;

    private Context context;
    private FragmentManager fragmentManager;

    private boolean startWithTextSelection;
    private boolean startWithFileSelection;

    private boolean isTextSelected;

    private final HashCalculatorTask.HashCalculatorTaskTarget hashCalculatorTaskTarget = hashValue -> {
        if (hashValue == null) {
            etGeneratedHash.setText("");
            showSnackbarWithoutAction(
                    getString(R.string.message_invalid_selected_source)
            );
        } else {
            etGeneratedHash.setText(hashValue);
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    };

    // CPD-OFF
    @Override
    public void onAttach(@NonNull Context context) {
        App.appComponent.inject(this);
        super.onAttach(context);
    }
    // CPD-ON

    private void showSnackbarWithoutAction(
            @NonNull String message
    ) {
        new AppSnackbar(
                context,
                mainScreen,
                message,
                themeHelper
        ).show();
    }

    @Override
    public void userActionSelect(
            @NonNull UserActionType userActionType
    ) {
        switch (userActionType) {
            case ENTER_TEXT:
                enterText();
                break;
            case SEARCH_FILE:
                searchFile();
                break;
            case GENERATE_HASH:
                generateHash();
                break;
            case COMPARE_HASHES:
                compareHashes();
                break;
            case EXPORT_AS_TXT:
                saveGeneratedHashAsTextFile();
                break;
            default:
                throw new IllegalArgumentException(
                        "Unknown UserActionType"
                );
        }
    }

    private void searchFile() {
        try {
            Intent openExplorerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            openExplorerIntent.addCategory(Intent.CATEGORY_OPENABLE);
            openExplorerIntent.setType("*/*");
            startActivityForResult(
                    openExplorerIntent,
                    FILE_SELECT
            );
        } catch (ActivityNotFoundException e) {
            LogUtils.e(e);
            showSnackbarWithoutAction(
                    getString(R.string.message_error_start_file_selector)
            );
        }
    }

    @SuppressLint("ResourceType")
    private void generateHash() {
        if (fileUri != null || isTextSelected) {
            HashType hashType = HashType.getHashTypeFromString(
                    tvSelectedHashType.getText().toString()
            );
            progressDialog = new AppProgressDialog(
                    context,
                    R.string.message_generate_dialog,
                    themeHelper
            ).build();
            progressDialog.show();
            if (isTextSelected) {
                new HashCalculatorTask(
                        context,
                        hashType,
                        tvSelectedObjectName.getText().toString(),
                        hashCalculatorTaskTarget
                ).execute();
            } else {
                new HashCalculatorTask(
                        context,
                        hashType,
                        fileUri,
                        hashCalculatorTaskTarget
                ).execute();
            }
        } else {
            showSnackbarWithoutAction(
                    getString(R.string.message_select_object)
            );
        }
    }

    private void compareHashes() {
        if (TextUtils.fieldIsNotEmpty(etCustomHash)
                && TextUtils.fieldIsNotEmpty(etGeneratedHash)) {
            boolean equal = TextUtils.compareText(
                    etCustomHash.getText().toString(),
                    etGeneratedHash.getText().toString()
            );
            showSnackbarWithoutAction(
                    getString(
                            equal ? R.string.message_match_result
                                    : R.string.message_do_not_match_result
                    )
            );
        } else {
            showSnackbarWithoutAction(
                    getString(R.string.message_fill_fields)
            );
        }
    }

    private void selectHashTypeFromList() {
        GenerateToBottomSheet generateToBottomSheet = new GenerateToBottomSheet();
        generateToBottomSheet.show(
                getFragmentManager(),
                generateToBottomSheet.getClass().getCanonicalName()
        );
    }

    private void saveGeneratedHashAsTextFile() {
        if ((fileUri != null
                || isTextSelected) && TextUtils.fieldIsNotEmpty(etGeneratedHash)) {
            String filename = getString(
                    isTextSelected
                            ? R.string.filename_hash_from_text
                            : R.string.filename_hash_from_file
            );
            saveTextFile(filename);
        } else {
            showSnackbarWithoutAction(
                    getString(R.string.message_generate_hash_before_export)
            );
        }
    }

    private void saveTextFile(@NonNull String filename) {
        try {
            Intent saveTextFileIntent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            saveTextFileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            saveTextFileIntent.setType("text/plain");
            saveTextFileIntent.putExtra(
                    Intent.EXTRA_TITLE,
                    filename + ".txt"
            );
            startActivityForResult(
                    saveTextFileIntent,
                    SettingsHelper.FILE_CREATE
            );
        } catch (ActivityNotFoundException e) {
            LogUtils.e(e);
            showSnackbarWithoutAction(
                    getString(R.string.message_error_start_file_selector)
            );
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (checkArguments(bundle)) {
            checkShortcutActionPresence(bundle);
        }
    }

    private boolean checkArguments(@Nullable Bundle bundle) {
        return bundle != null;
    }

    private void checkShortcutActionPresence(
            @NonNull Bundle shortcutsArguments
    ) {
        startWithTextSelection = shortcutsArguments.getBoolean(
                App.ACTION_START_WITH_TEXT,
                false
        );
        startWithFileSelection = shortcutsArguments.getBoolean(
                App.ACTION_START_WITH_FILE,
                false
        );

        shortcutsArguments.remove(App.ACTION_START_WITH_TEXT);
        shortcutsArguments.remove(App.ACTION_START_WITH_FILE);
    }

    private void validateSelectedFile(@Nullable Uri uri) {
        if (uri != null) {
            fileUri = uri;
            isTextSelected = false;
            setResult(fileNameFromUri(fileUri), false);
        }
    }

    @NonNull
    private String fileNameFromUri(@NonNull Uri uri) {
        String scheme = uri.getScheme();
        if (scheme != null && scheme.equals("content")) {
            try (Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null)) {
                assert cursor != null;
                cursor.moveToPosition(0);
                return cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                OpenableColumns.DISPLAY_NAME
                        )
                );
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
        return new File(uri.getPath()).getName();
    }

    @Override
    public void textValueEntered(@NonNull String text) {
        setResult(text, true);
    }

    private void setResult(
            @NonNull String text,
            boolean isText
    ) {
        tvSelectedObjectName.setText(text);
        this.isTextSelected = isText;
        btnGenerateFrom.setText(
                getString(
                        isText ? R.string.common_text : R.string.common_file
                )
        );
    }

    private void enterText() {
        String currentText = !isTextSelected
                ? null
                : tvSelectedObjectName.getText().toString();
        new TextInputDialog(
                context,
                this,
                currentText
        ).show();
    }

    @Override
    public void appBackClick() {
        if (doubleBackToExitPressedTwice) {
            getActivity().finish();
        }else {
            this.doubleBackToExitPressedTwice = true;
            Toast.makeText(getContext(),R.string.message_double_click, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedTwice =false;
                }
            }, 2000);
        }
    }

    private void showSnachbarWithAction(
            @NonNull View mainScreen,
            @NonNull String message,
            @NonNull String actionText,
            @NonNull View.OnClickListener action
    ) {
        new AppSnackbar(
                context,
                mainScreen,
                message,
                actionText,
                action,
                themeHelper
        ).show();
    }

    private void validateTextCase() {
        boolean useUpperCase = settingsHelper.useUpperCase();
        InputFilter[] fieldFilters = useUpperCase
                ? new InputFilter[]{new InputFilter.AllCaps()}
                : new InputFilter[]{};
        etCustomHash.setFilters(fieldFilters);
        etGeneratedHash.setFilters(fieldFilters);

        if (useUpperCase) {
            TextUtils.convertToUpperCase(etCustomHash);
            TextUtils.convertToUpperCase(etGeneratedHash);
        } else {
            TextUtils.convertToLowerCase(etCustomHash);
            TextUtils.convertToLowerCase(etGeneratedHash);
        }

        etCustomHash.setSelection(
                etCustomHash.getText().length()
        );
        etGeneratedHash.setSelection(
                etGeneratedHash.getText().length()
        );
    }

    @Override
    public void hashTypeSelect(
            @NonNull HashType hashType
    ) {
        tvSelectedHashType.setText(
                hashType.getTypeAsString()
        );
        settingsHelper.saveHashTypeAsLast(
                hashType
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        mainScreen = view.findViewById(R.id.fl_main_screen);

        etCustomHash = view.findViewById(R.id.et_field_custom_hash);
        etGeneratedHash = view.findViewById(R.id.et_field_generated_hash);

        ImageView ivCustomHashCopy = view.findViewById(R.id.iv_custom_hash_copy);
        ivCustomHashCopy.setOnClickListener(v -> copyHashFromEditText(etCustomHash));

        ImageView ivCustomHashClear = view.findViewById(R.id.iv_custom_hash_clear);
        ivCustomHashClear.setOnClickListener(v -> etCustomHash.setText(""));

        ImageView ivGeneratedHashCopy = view.findViewById(R.id.iv_generated_hash_copy);
        ivGeneratedHashCopy.setOnClickListener(v -> copyHashFromEditText(etGeneratedHash));

        ImageView ivGeneratedHashClear = view.findViewById(R.id.iv_generated_hash_clear);
        ivGeneratedHashClear.setOnClickListener(v -> etGeneratedHash.setText(""));

        etGeneratedHash.addTextChangedListener(
                watcherForInputField(
                        ivGeneratedHashCopy,
                        ivGeneratedHashClear
                )
        );
        etCustomHash.addTextChangedListener(
                watcherForInputField(
                        ivCustomHashCopy,
                        ivCustomHashClear
                )
        );

        tvSelectedObjectName = view.findViewById(R.id.tv_selected_object_name);

        tvSelectedHashType = view.findViewById(R.id.tv_selected_hash_type);
        tvSelectedHashType.setOnClickListener(v -> selectHashTypeFromList());

        btnGenerateFrom = view.findViewById(R.id.btn_generate_from);
        btnGenerateFrom.setOnClickListener(v -> showSourceSelectDialog());

        Button btnHashActions = view.findViewById(R.id.btn_hash_actions);
        btnHashActions.setOnClickListener(v -> showActionSelectDialog());

        fragmentManager = getActivity().getSupportFragmentManager();
        tvSelectedHashType.setText(
                settingsHelper.getLastHashType().getTypeAsString()
        );
        tvSelectedObjectName.setMovementMethod(
                new ScrollingMovementMethod()
        );
        if (startWithTextSelection) {
            userActionSelect(UserActionType.ENTER_TEXT);
            startWithTextSelection = false;
        } else if (startWithFileSelection) {
            userActionSelect(UserActionType.SEARCH_FILE);
            startWithFileSelection = false;
        }

        Bundle bundle = getArguments();
        if (checkArguments(bundle)) {
            checkExternalDataPresence(bundle);
        }
    }

    @NonNull
    @Override
    protected LangHelper langHelper() {
        return langHelper;
    }

    @NonNull
    @SuppressWarnings("MethodParametersAnnotationCheck")
    private TextWatcher watcherForInputField(
            @NonNull ImageView copyButton,
            @NonNull ImageView clearButton
    ) {
        return new AppTextWatcher() {
            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count
            ) {
                int writtenTextLength = s.toString().length();
                if (writtenTextLength > 0) {
                    copyButton.setVisibility(View.VISIBLE);
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    copyButton.setVisibility(View.INVISIBLE);
                    clearButton.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    private void copyHashFromEditText(
            @NonNull EditText editText
    ) {
        String hash = editText.getText().toString();
        if (!hash.isEmpty()) {
            new Clipboard(
                    context,
                    hash
            ).copy();
            showHashCopySnackbar();
        }
    }

    private void showSourceSelectDialog() {
        SourceSelectActionsBottomSheet sourceSelectActionsBottomSheet
                = new SourceSelectActionsBottomSheet();
        sourceSelectActionsBottomSheet.show(
                fragmentManager,
                sourceSelectActionsBottomSheet.getClass().getCanonicalName()
        );
    }

    private void showActionSelectDialog() {
        ActionSelectActionsBottomSheet actionSelectActionsBottomSheet
                = new ActionSelectActionsBottomSheet();
        actionSelectActionsBottomSheet.show(
                fragmentManager,
                actionSelectActionsBottomSheet.getClass().getCanonicalName()
        );
    }

    private void showHashCopySnackbar() {
        showSnackbarWithoutAction(
                getString(R.string.history_item_click_text)
        );
    }

    private void checkExternalDataPresence(@NonNull Bundle dataArguments) {
        String uri = dataArguments.getString(MainActivity.URI_FROM_EXTERNAL_APP);
        if (uri != null) {
            validateSelectedFile(Uri.parse(uri));
            dataArguments.remove(MainActivity.URI_FROM_EXTERNAL_APP);
        }
    }


    @Override
    public void appResume() {
        super.appResume();
        validateTextCase();
        checkMultilinePreference();
        checkFileManagerChanged();
        hashTypeSelect(
                settingsHelper.getLastHashType()
        );
    }

    private void checkMultilinePreference() {
        if (settingsHelper.isUsingMultilineHashFields()) {
            validateMultilineFields(
                    etCustomHash,
                    TEXT_MULTILINE_LINES_COUNT,
                    false
            );
            validateMultilineFields(
                    etGeneratedHash,
                    TEXT_MULTILINE_LINES_COUNT,
                    false
            );
        } else {
            validateMultilineFields(
                    etCustomHash,
                    TEXT_SINGLE_LINE_LINES_COUNT,
                    true
            );
            validateMultilineFields(
                    etGeneratedHash,
                    TEXT_SINGLE_LINE_LINES_COUNT,
                    true
            );
        }
    }

    private void validateMultilineFields(
            @NonNull EditText editText,
            int lines,
            boolean singleLine
    ) {
        editText.setSingleLine(singleLine);
        editText.setMinLines(lines);
        editText.setMaxLines(lines);
        editText.setLines(lines);
    }

    private void checkFileManagerChanged() {
        if (settingsHelper.refreshSelectedFile()) {
            if (!isTextSelected && fileUri != null) {
                fileUri = null;
                tvSelectedObjectName.setText(getString(R.string.message_select_object));
                btnGenerateFrom.setText(getString(R.string.action_from));
            }
            settingsHelper.setRefreshSelectedFileStatus(false);
        }
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            @Nullable Intent data
    ) {
        if (data != null
                && requestCode == FILE_SELECT
                && resultCode == Activity.RESULT_OK) {
            validateSelectedFile(data.getData());
            settingsHelper.setGenerateFromShareIntentMode(false);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hash_calculator;
    }

    @Override
    public int getActionBarTitleResId() {
        return R.string.common_app_name;
    }

    @Override
    public int getMenuResId() {
        return R.menu.menu_main;
    }

    @Override
    public boolean setAllowBackAction() {
        return false;
    }

}
