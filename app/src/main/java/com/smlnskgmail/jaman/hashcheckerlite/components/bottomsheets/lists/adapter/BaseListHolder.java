package com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.ListItem;
import com.smlnskgmail.jaman.hashcheckerlite.logic.themes.api.ThemeHelper;

import static com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.ListItem.DEFAULT_ICON_VALUE;

public abstract class BaseListHolder<T extends ListItem> extends RecyclerView.ViewHolder {

    private final TextView tvItemTitle;

    private final ImageView ivItemPrimaryIcon;
    private final ImageView ivItemAdditionalIcon;

    private final Context context;
    private final ThemeHelper themeHelper;

    protected BaseListHolder(
            @NonNull Context themeContext,
            @NonNull View itemView,
            @NonNull ThemeHelper themeHelper
    ) {
        super(itemView);

        // Context with current theme
        context = themeContext;
        this.themeHelper = themeHelper;
        tvItemTitle = itemView.findViewById(R.id.tv_item_list_title);
        ivItemPrimaryIcon = itemView.findViewById(R.id.iv_item_list_icon);
        ivItemAdditionalIcon = itemView.findViewById(
                R.id.iv_item_list_additional_icon
        );
    }

    protected void bind(
            @NonNull final T listItem
    ) {
        itemView.setOnClickListener(v -> callItemClick());
        tvItemTitle.setText(listItem.getTitle(context));
        int primaryIconResId = listItem.getPrimaryIconResId();
        if (primaryIconResId != DEFAULT_ICON_VALUE) {
            ivItemPrimaryIcon.setImageResource(
                    listItem.getPrimaryIconResId()
            );
            themeHelper.applyAccentColorToImage(
                    ivItemPrimaryIcon.getDrawable()
            );
        }
        ivItemPrimaryIcon.setVisibility(
                getConditionToPrimaryIconVisibleState()
                        ? View.VISIBLE
                        : View.GONE
        );
        int additionalIconResId = listItem.getAdditionalIconResId();
        if (additionalIconResId != DEFAULT_ICON_VALUE) {
            ivItemAdditionalIcon.setImageResource(
                    listItem.getAdditionalIconResId()
            );
            themeHelper.applyAccentColorToImage(
                    ivItemAdditionalIcon.getDrawable()
            );
        }
        ivItemAdditionalIcon.setVisibility(
                getConditionToAdditionalIconVisibleState()
                        ? View.VISIBLE
                        : View.INVISIBLE
        );
    }

    protected boolean getConditionToPrimaryIconVisibleState() {
        return false;
    }

    protected boolean getConditionToAdditionalIconVisibleState() {
        return false;
    }

    protected void callItemClick() {

    }

    @NonNull
    protected ImageView getIvItemAdditionalIcon() {
        return ivItemAdditionalIcon;
    }

    @NonNull
    protected Context getContext() {
        return context;
    }

}
