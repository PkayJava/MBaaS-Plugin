package com.angkorteam.mbaas.server.select2;

import com.angkorteam.framework.extension.wicket.markup.html.form.select2.MultipleChoiceProvider;
import com.angkorteam.framework.extension.wicket.markup.html.form.select2.Option;
import com.google.gson.Gson;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * Created by socheat on 12/5/16.
 */
public class JdbcMultipleChoiceProvider extends MultipleChoiceProvider<Item> {


    public JdbcMultipleChoiceProvider(String table, String idField) {
        throw new UnsupportedOperationException();
    }

    public JdbcMultipleChoiceProvider(String table, String idField, String valueField) {
        throw new UnsupportedOperationException();
    }

    public JdbcMultipleChoiceProvider(String table, String idField, String queryField, String labelField) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Item> toChoices(List<String> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Option> query(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMore(String s, int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Gson getGson() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDisplayValue(Item object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIdValue(Item object, int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item getObject(String id, IModel<? extends List<? extends Item>> choices) {
        throw new UnsupportedOperationException();
    }

    public void addWhere(String filter) {
        throw new UnsupportedOperationException();
    }

}
