package by.epam.afc.service.util;

import by.epam.afc.dao.entity.BaseEntity;

import static by.epam.afc.dao.entity.BaseEntity.UNDEFINED_ID;

public class EntityChecker {
    private static final EntityChecker instance = new EntityChecker();

    private EntityChecker() {
    }

    public static EntityChecker getInstance() {
        return instance;
    }

    public boolean check(BaseEntity entity) {
        return entity.getId() != UNDEFINED_ID;
    }
}
