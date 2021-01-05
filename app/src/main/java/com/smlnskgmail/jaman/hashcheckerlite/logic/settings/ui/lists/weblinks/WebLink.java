package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks;

import android.content.Context;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashcheckerlite.R;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.ListItem;

import java.util.Arrays;
import java.util.List;

public enum WebLink implements ListItem {

    SOURCE_CODE(
            R.string.title_web_link_github,
            R.drawable.ic_github,
            R.string.web_link_source_code
    ),
    GOOGLE_PLAY(
            R.string.title_web_link_google_play,
            R.drawable.ic_google_play,
            R.string.web_link_my_apps
    ),
    PRIVACY_POLICY(
            R.string.settings_title_privacy_policy,
            R.drawable.ic_settings_privacy_policy,
            R.string.web_link_privacy_policy
    );

    private final int titleResId;
    private final int iconResId;
    private final int linkResId;

    WebLink(
            int titleResId,
            int iconResId,
            int linkResId
    ) {
        this.titleResId = titleResId;
        this.iconResId = iconResId;
        this.linkResId = linkResId;
    }

    public int getLinkResId() {
        return linkResId;
    }

    @NonNull
    @Override
    public String getTitle(@NonNull Context context) {
        return context.getString(titleResId);
    }

    @Override
    public int getPrimaryIconResId() {
        return iconResId;
    }

    @Override
    public int getAdditionalIconResId() {
        return DEFAULT_ICON_VALUE;
    }

    @NonNull
    public static List<WebLink> getAuthorLinks() {
        return Arrays.asList(
                SOURCE_CODE,
                GOOGLE_PLAY
        );
    }

}

