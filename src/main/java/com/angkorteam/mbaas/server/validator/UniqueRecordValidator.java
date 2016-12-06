package com.angkorteam.mbaas.server.validator;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

/**
 * Created by socheat on 12/6/16.
 */
public class UniqueRecordValidator<T> implements IValidator<T> {


    public UniqueRecordValidator(String tableName, String fieldName) {
        throw new UnsupportedOperationException();
    }

    public UniqueRecordValidator(String tableName, String fieldName, String idFieldName, T idFieldValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void validate(IValidatable<T> validatable) {
        throw new UnsupportedOperationException();
    }

}
