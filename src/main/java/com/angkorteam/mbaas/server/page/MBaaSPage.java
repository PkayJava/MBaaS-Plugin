package com.angkorteam.mbaas.server.page;

import com.angkorteam.mbaas.model.entity.tables.pojos.MenuItemPojo;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.border.Border;

import java.util.List;

/**
 * Created by socheat on 11/19/16.
 */
public abstract class MBaaSPage extends WebPage implements UUIDPage {

    @Override
    protected final void onInitialize() {
        throw new UnsupportedOperationException();
    }

    protected abstract void doInitialize(Border layout);

    protected final Border getLayout() {
        throw new UnsupportedOperationException();
    }

    public MenuItemPojo getMenuItem() {
        throw new UnsupportedOperationException();
    }

    protected List<String> initBreadcrumb() {
        throw new UnsupportedOperationException();
    }

    public boolean isMenuWidgetSelected(String menuId) {
        throw new UnsupportedOperationException();
    }

    public boolean isMenuItemWidgetSelected(String menuItemId) {
        throw new UnsupportedOperationException();
    }

    public String getHttpAddress() {
        throw new UnsupportedOperationException();
    }
}