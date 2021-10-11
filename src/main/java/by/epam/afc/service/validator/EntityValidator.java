package by.epam.afc.service.validator;

import by.epam.afc.dao.entity.BaseEntity;

public interface EntityValidator {
    boolean validate(BaseEntity entity);
}
