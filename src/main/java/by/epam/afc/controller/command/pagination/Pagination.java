package by.epam.afc.controller.command.pagination;

import by.epam.afc.dao.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class Pagination<T extends BaseEntity> {
    private boolean previous;
    private boolean next;
    private int currentPage;
    private List<T> currentData;

    public Pagination(boolean previous, boolean next, List<T> currentData) {
        this.previous = previous;
        this.next = next;
        this.currentData = currentData;
    }

    public Pagination(){}

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getCurrentData() {
        return new ArrayList<>(currentData);
    }

    public void setCurrentData(List<T> currentData) {
        this.currentData = new ArrayList<>(currentData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pagination<?> that = (Pagination<?>) o;

        if (previous != that.previous) return false;
        if (next != that.next) return false;
        if (currentPage != that.currentPage) return false;
        return currentData != null ? currentData.equals(that.currentData) : that.currentData == null;
    }

    @Override
    public int hashCode() {
        int result = (previous ? 1 : 0);
        result = 31 * result + (next ? 1 : 0);
        result = 31 * result + currentPage;
        result = 31 * result + (currentData != null ? currentData.hashCode() : 0);
        return result;
    }
}
