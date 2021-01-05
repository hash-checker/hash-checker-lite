package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smlnskgmail.jaman.hashcheckerlite.components.BaseFragment;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;

import java.util.Arrays;

public class GenerateToBottomSheet extends BaseListBottomSheet<HashType> {

    @NonNull
    @Override
    public BaseListAdapter<HashType> getItemsAdapter() {
        Fragment fragment = getFragmentManager().findFragmentByTag(
                BaseFragment.CURRENT_FRAGMENT_TAG
        );
        return new HashesListAdapter(
                Arrays.asList(HashType.values()),
                this,
                (HashTypeSelectTarget) fragment
        );
    }

}
