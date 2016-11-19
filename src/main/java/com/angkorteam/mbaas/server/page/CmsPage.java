package com.angkorteam.mbaas.server.page;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Created by socheat on 11/19/16.
 */
public abstract class CmsPage extends MBaaSPage implements IMarkupResourceStreamProvider {

    @Override
    public final String getVariation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
        throw new UnsupportedOperationException();
    }

}