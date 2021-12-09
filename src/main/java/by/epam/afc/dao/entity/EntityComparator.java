package by.epam.afc.dao.entity;

import java.util.Comparator;

public class EntityComparator<T extends BaseEntity> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
