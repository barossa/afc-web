package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.BaseEntity;
import by.epam.afc.service.validator.EntityValidator;

import static by.epam.afc.dao.entity.BaseEntity.UNDEFINED_ID;

public class EntityValidatorImpl implements EntityValidator {
    private static final EntityValidatorImpl instance = new EntityValidatorImpl();

    private EntityValidatorImpl() {
    }

    public static EntityValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validate(BaseEntity entity) {
        return entity.getId() > 0;
    }
}
