package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListHolder;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.Action;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.types.UserActionTarget;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.List;

public class ActionsListAdapter extends BaseListAdapter<Action> {

    private final UserActionTarget userActionTarget;
    private final ThemeHelper themeHelper;

    ActionsListAdapter(
            @NonNull List<Action> items,
            @NonNull BaseListBottomSheet<Action> bottomSheet,
            @NonNull UserActionTarget userActionTarget,
            @NonNull ThemeHelper themeHelper
    ) {
        super(items, bottomSheet);
        this.userActionTarget = userActionTarget;
        this.themeHelper = themeHelper;
    }

    @NonNull
    @Override
    protected BaseListHolder<Action> getItemsHolder(
            @NonNull Context themeContext,
            @NonNull View view
    ) {
        return new ActionHolder(
                themeContext,
                view
        );
    }

    private class ActionHolder extends BaseListHolder<Action> {

        private Action action;

        ActionHolder(
                @NonNull Context themeContext,
                @NonNull View itemView
        ) {
            super(themeContext, itemView, themeHelper);
        }

        @Override
        protected void bind(@NonNull Action listItem) {
            action = listItem;
            super.bind(listItem);
        }

        @Override
        protected void callItemClick() {
            userActionTarget.userActionSelect(
                    action.getUserActionType()
            );
            dismissBottomSheet();
        }

        @Override
        protected boolean getConditionToPrimaryIconVisibleState() {
            return true;
        }

    }

}
