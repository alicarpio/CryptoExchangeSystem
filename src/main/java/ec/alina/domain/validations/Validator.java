package ec.alina.domain.validations;

import ec.alina.domain.validations.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
