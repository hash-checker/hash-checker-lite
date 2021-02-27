package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks;

import androidx.annotation.NonNull;

import java.util.List;

public class AuthorWebLinksBottomSheet extends WebLinksBottomSheet {

    @NonNull
    @Override
    List<WebLink> getLinks() {
        return WebLink.getAuthorLinks();
    }

}
