package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.components.BaseFragment;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.settings.api.SettingsHelper;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.Arrays;

import javax.inject.Inject;

public class GenerateToBottomSheet extends BaseListBottomSheet<HashType> {

    @Inject
    public SettingsHelper settingsHelper;

    @Inject
    public ThemeHelper themeHelper;

    // CPD-OFF
    @Override
    public void onAttach(@NonNull Context context) {
        App.appComponent.inject(this);
        super.onAttach(context);
    }
    // CPD-ON

    @NonNull
    @Override
    public BaseListAdapter<HashType> getItemsAdapter() {
        Fragment fragment = getFragmentManager().findFragmentByTag(
                BaseFragment.CURRENT_FRAGMENT_TAG
        );
        return new HashesListAdapter(
                Arrays.asList(HashType.values()),
                this,
                (HashTypeSelectTarget) fragment,
                settingsHelper.getLastHashType(),
                themeHelper
        );
    }

}
