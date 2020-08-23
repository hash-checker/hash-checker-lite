package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks;

import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.BaseListBottomSheet;
import com.smlnskgmail.jaman.hashcheckerlite.components.bottomsheets.lists.adapter.BaseListAdapter;

import java.util.List;

abstract class WebLinksBottomSheet extends BaseListBottomSheet<WebLink> {

    @Override
    public BaseListAdapter<WebLink> getItemsAdapter() {
        return new WebLinksListAdapter(
                getLinks(),
                this
        );
    }

    abstract List<WebLink> getLinks();

}
