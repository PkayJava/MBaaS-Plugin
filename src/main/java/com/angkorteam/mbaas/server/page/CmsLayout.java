package com.angkorteam.mbaas.server.page;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Created by socheat on 12/1/16.
 */
public abstract class CmsLayout extends Border implements IMarkupResourceStreamProvider, UUIDLayout {

    protected CmsLayout(String id) {
        super(id);
    }

    @Override
    protected final void onInitialize() {
        throw new UnsupportedOperationException();
    }

    protected abstract void doInitialize();

    @Override
    public final String getVariation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        throw new UnsupportedOperationException();
    }

}
