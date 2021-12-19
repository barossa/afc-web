package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.BaseEntity;
import by.epam.afc.service.validator.EntityValidator;

/**
 * The type Entity validator.
 */
public class EntityValidatorImpl implements EntityValidator {
    private static final EntityValidatorImpl instance = new EntityValidatorImpl();

    private EntityValidatorImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EntityValidatorImpl getInstance() {
        return instance;
    }

    /**
     * Validate entity boolean.
     *
     * @param entity the entity
     * @return the boolean
     */
    @Override
    public boolean validate(BaseEntity entity) {
        return entity.getId() > 0;
    }
}
