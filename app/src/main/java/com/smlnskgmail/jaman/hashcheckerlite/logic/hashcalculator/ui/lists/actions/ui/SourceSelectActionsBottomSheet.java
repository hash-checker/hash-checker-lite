package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.App;
import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.Action;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class SourceSelectActionsBottomSheet extends ActionsBottomSheet {

    @Inject
    ThemeHelper themeHelper;

    // CPD-OFF
    @Override
    public void onAttach(@NonNull Context context) {
        App.appComponent.inject(this);
        super.onAttach(context);
    }
    // CPD-ON

    @NonNull
    @Override
    protected ThemeHelper themeHelper() {
        return themeHelper;
    }

    @NonNull
    @Override
    List<Action> getActions() {
        return Arrays.asList(
                Action.TEXT,
                Action.FILE
        );
    }

}
