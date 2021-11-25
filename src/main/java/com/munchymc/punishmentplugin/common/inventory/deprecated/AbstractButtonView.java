package com.munchymc.punishmentplugin.common.inventory.deprecated;

import com.munchymc.punishmentplugin.common.inventory.deprecated.page.Page;
@Deprecated
public abstract class AbstractButtonView<T> extends Page {
    private AbstractButtonView nextPage;
    private AbstractButtonView lastPage;

    public AbstractButtonView getNextPage() {
        return nextPage;
    }

    public AbstractButtonView getLastPage() {
        return lastPage;
    }
}
