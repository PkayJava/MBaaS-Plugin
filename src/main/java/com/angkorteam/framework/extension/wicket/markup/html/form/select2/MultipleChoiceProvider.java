package com.angkorteam.framework.extension.wicket.markup.html.form.select2;

import java.util.List;

/**
 * Created by socheat on 5/25/16.
 */
public abstract class MultipleChoiceProvider<T> extends IChoiceProvider<T> {

    public abstract List<T> toChoices(List<String> ids);

}
