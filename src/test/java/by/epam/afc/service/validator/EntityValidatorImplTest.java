package by.epam.afc.service.validator;

import by.epam.afc.dao.entity.BaseEntity;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.service.validator.impl.EntityValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityValidatorImplTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 25, 34, 105, 103231})
    public void validEntity(int id) {
        BaseEntity entity = new Category(id);
        EntityValidator validator = EntityValidatorImpl.getInstance();
        boolean actual = validator.validate(entity);
        Assertions.assertTrue(actual);

    }

    @ParameterizedTest
    @ValueSource(ints = {-9, 0, -2, -4, -5001})
    public void invalidEntity(int id) {
        BaseEntity entity = new Category(id);
        EntityValidator validator = EntityValidatorImpl.getInstance();
        boolean actual = validator.validate(entity);
        Assertions.assertFalse(actual);
    }
}
