package by.epam.afc.controller.command;

import by.epam.afc.dao.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Pagination<T extends BaseEntity> {
    private List<T> data;
    private boolean next;
    private boolean previous;
    private int currentPage;
    private Map<String, List<String>> requestAttributes;

    public Pagination() {
        requestAttributes = new HashMap<>();
    }

    public Pagination(List<T> data, boolean next, boolean previous, int currentPage) {
        this();
        this.data = data;
        this.next = next;
        this.previous = previous;
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Map<String, List<String>> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(Map<String, List<String>> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination<?> that = (Pagination<?>) o;

        if (next != that.next) return false;
        if (previous != that.previous) return false;
        if (currentPage != that.currentPage) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return requestAttributes != null ? requestAttributes.equals(that.requestAttributes) : that.requestAttributes == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (next ? 1 : 0);
        result = 31 * result + (previous ? 1 : 0);
        result = 31 * result + currentPage;
        result = 31 * result + (requestAttributes != null ? requestAttributes.hashCode() : 0);
        return result;
    }
}