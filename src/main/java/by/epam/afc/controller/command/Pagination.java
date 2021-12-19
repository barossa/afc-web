package by.epam.afc.controller.command;

import by.epam.afc.dao.entity.BaseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Pagination.
 *
 * @param <T> the type parameter
 */
public final class Pagination<T extends BaseEntity> {
    private List<T> data;
    private boolean next;
    private boolean previous;
    private int currentPage;
    private Map<String, List<String>> requestAttributes;

    /**
     * Instantiates a new Pagination.
     */
    public Pagination() {
        requestAttributes = new HashMap<>();
    }

    /**
     * Instantiates a new Pagination.
     *
     * @param data        the data of the page
     * @param next        the next page exists
     * @param previous    the previous page exists
     * @param currentPage the current page number
     */
    public Pagination(List<T> data, boolean next, boolean previous, int currentPage) {
        this();
        this.data = data;
        this.next = next;
        this.previous = previous;
        this.currentPage = currentPage;
    }

    /**
     * Gets data.
     *
     * @return the page data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the page data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * Is next boolean.
     *
     * @return the boolean
     */
    public boolean isNext() {
        return next;
    }

    /**
     * Sets next.
     *
     * @param next the next exists
     */
    public void setNext(boolean next) {
        this.next = next;
    }

    /**
     * Is previous boolean.
     *
     * @return the boolean
     */
    public boolean isPrevious() {
        return previous;
    }

    /**
     * Sets previous.
     *
     * @param previous the previous exists
     */
    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    /**
     * Gets current page.
     *
     * @return the current page number
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets current page.
     *
     * @param currentPage the current page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets request attributes.
     *
     * @return the request attributes
     */
    public Map<String, List<String>> getRequestAttributes() {
        return requestAttributes;
    }

    /**
     * Sets request attributes.
     *
     * @param requestAttributes the request attributes
     */
    public void setRequestAttributes(Map<String, List<String>> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
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

    /**
     * Hash code int.
     *
     * @return the int
     */
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