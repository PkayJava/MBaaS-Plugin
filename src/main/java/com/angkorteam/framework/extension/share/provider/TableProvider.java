package com.angkorteam.framework.extension.share.provider;

import com.angkorteam.framework.extension.wicket.extensions.markup.html.repeater.data.table.filter.Calendar;

/**
 * Created by socheat on 12/8/16.
 */
public interface TableProvider {

    void selectField(String aliasName, Class<?> columnClass);

    void selectField(String aliasName, Calendar calendar);

    void selectField(String aliasName, String pattern, Calendar date);

    void boardField(String jdbcColumn, String aliasName, Class<?> clazz);

    void boardField(String jdbcColumn, String aliasName, Calendar calendar);

}
