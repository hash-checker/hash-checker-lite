package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks;

import java.util.List;

public class AuthorWebLinksBottomSheet extends WebLinksBottomSheet {

    @Override
    @androidx.annotation.NonNull
    List<WebLink> getLinks() {
        return WebLink.getAuthorLinks();
    }

}
