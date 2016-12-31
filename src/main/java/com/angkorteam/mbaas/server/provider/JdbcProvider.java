package com.angkorteam.mbaas.server.provider;

import com.angkorteam.framework.extension.share.provider.TableProvider;
import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by socheat on 12/8/16.
 */
public class JdbcProvider extends SortableDataProvider<Map<String, Object>, String> implements IFilterStateLocator<Map<String, String>>, TableProvider {

    public JdbcProvider(String from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void selectField(String aliasName, Class<?> columnClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void selectField(String aliasName, Calendar calendar) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void selectField(String aliasName, String pattern, Calendar date) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void boardField(String jdbcColumn, String aliasName, Class<?> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void boardField(String jdbcColumn, String aliasName, Calendar calendar) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, String> getFilterState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFilterState(Map<String, String> state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<? extends Map<String, Object>> iterator(long first, long count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IModel<Map<String, Object>> model(Map<String, Object> object) {
        throw new UnsupportedOperationException();
    }

    public void addWhere(String whereFilter) {
        throw new UnsupportedOperationException();
    }

    public void addHaving(String havingFilter) {
        throw new UnsupportedOperationException();
    }

    public void addJoin(String join) {
        throw new UnsupportedOperationException();
    }
}