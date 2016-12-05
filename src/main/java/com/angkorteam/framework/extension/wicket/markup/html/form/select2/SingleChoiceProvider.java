package com.angkorteam.framework.extension.wicket.markup.html.form.select2;

/**
 * Created by socheat on 5/25/16.
 */
public abstract class SingleChoiceProvider<T> extends IChoiceProvider<T> {

    public abstract T toChoice(String id);

}
