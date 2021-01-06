package com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.ui;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.logic.hashcalculator.ui.lists.actions.Action;

import java.util.Arrays;
import java.util.List;

public class SourceSelectActionsBottomSheet extends ActionsBottomSheet {

    @NonNull
    @Override
    List<Action> getActions() {
        return Arrays.asList(
                Action.TEXT,
                Action.FILE
        );
    }

}
