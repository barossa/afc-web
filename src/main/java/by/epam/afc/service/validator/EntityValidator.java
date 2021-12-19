package by.epam.afc.service.validator;

import by.epam.afc.dao.entity.BaseEntity;

/**
 * The interface Entity validator.
 */
public interface EntityValidator {
    /**
     * Validate entity boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    boolean validate(BaseEntity entity);
}
