package com.smlnskgmail.jaman.hashcheckerlite.logic.settings.ui.lists.weblinks;

import java.util.List;

public class AuthorWebLinksBottomSheet extends WebLinksBottomSheet {

    @Override
    List<WebLink> getLinks() {
        return WebLink.getAuthorLinks();
    }

}
