package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.hashtypes;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListHolder;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.HashType;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.List;

public class HashesListAdapter extends BaseListAdapter<HashType> {

    private final HashType selectedHashType;
    private final HashTypeSelectTarget hashTypeSelectListener;
    private final ThemeHelper themeHelper;

    HashesListAdapter(
            @NonNull List<HashType> items,
            @NonNull BaseListBottomSheet<HashType> bottomSheet,
            @NonNull HashTypeSelectTarget hashTypeSelectListener,
            @NonNull HashType selectedHashType,
            @NonNull ThemeHelper themeHelper
    ) {
        super(items, bottomSheet);
        this.hashTypeSelectListener = hashTypeSelectListener;
        this.selectedHashType = selectedHashType;
        this.themeHelper = themeHelper;
    }

    @NonNull
    @Override
    public BaseListHolder<HashType> getItemsHolder(
            @NonNull Context themeContext,
            @NonNull View view
    ) {
        return new HashesListHolder(
                themeContext,
                view
        );
    }

    private class HashesListHolder extends BaseListHolder<HashType> {

        private HashType hashTypeAtPosition;

        HashesListHolder(
                @NonNull Context themeContext,
                @NonNull View itemView
        ) {
            super(themeContext, itemView, themeHelper);
        }

        @Override
        protected void bind(@NonNull HashType listItem) {
            hashTypeAtPosition = listItem;
            super.bind(listItem);
        }

        @Override
        protected void callItemClick() {
            ImageView ivAdditionalIcon = getIvItemAdditionalIcon();
            boolean visible = ivAdditionalIcon.getVisibility() == View.VISIBLE;
            ivAdditionalIcon.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

            hashTypeSelectListener.hashTypeSelect(hashTypeAtPosition);
            dismissBottomSheet();
        }

        @Override
        protected boolean getConditionToAdditionalIconVisibleState() {
            return hashTypeAtPosition == selectedHashType;
        }

    }

}
