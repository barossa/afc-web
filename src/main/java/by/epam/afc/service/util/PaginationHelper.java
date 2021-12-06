package by.epam.afc.service.util;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class PaginationHelper {
    private static final PaginationHelper instance = new PaginationHelper();

    private PaginationHelper() {
    }

    public static PaginationHelper getInstance() {
        return instance;
    }

    public <T extends BaseEntity> Pagination<T> getPage(List<T> elements, int page, int elementsOnPage) {
        int from = page * elementsOnPage;
        int to = from + elementsOnPage - 1;
        int elementsSize = elements.size();

        List<T> pageElements;
        if (elementsSize > from) {
            if (elementsSize > to) {
                pageElements = elements.subList(from, to);
                pageElements.add(elements.get(to));
            } else {
                pageElements = elements.subList(from, elementsSize - 1);
                pageElements.add(elements.get(elementsSize - 1));
            }
        } else {
            pageElements = new ArrayList<>();
        }
        boolean previous = page > 0;
        boolean next = elementsSize > (to + 1);
        return new Pagination<>(pageElements, next, previous, page);
    }
}
