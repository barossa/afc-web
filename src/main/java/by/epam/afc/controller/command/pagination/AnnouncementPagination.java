package by.epam.afc.controller.command.pagination;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Announcement.Status;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementPagination extends Pagination<Announcement> {
    private List<Region> regions;
    private List<Category> categories;
    private int rangeMin;
    private int rangeMax;
    private String searchRequest;
    private Status status;

    public AnnouncementPagination(List<Region> regions, List<Category> categories, int rangeMin, int rangeMax, String searchRequest, Status status) {
        this.regions = regions;
        this.categories = categories;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.searchRequest = searchRequest;
        this.status = status;
    }

    public AnnouncementPagination(Status status) {
        regions = new ArrayList<>();
        categories = new ArrayList<>();
        rangeMin = 0;
        rangeMax = 0;
        searchRequest = "";
        this.status = status;
    }

    public List<Region> getRegions() {
        return new ArrayList<>(regions);
    }

    public void setRegions(List<Region> regions) {
        this.regions = new ArrayList<>(regions);
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public void setCategories(List<Category> categories) {
        this.categories = new ArrayList<>(categories);
    }

    public int getRangeMin() {
        return rangeMin;
    }

    public void setRangeMin(int rangeMin) {
        this.rangeMin = rangeMin;
    }

    public int getRangeMax() {
        return rangeMax;
    }

    public void setRangeMax(int rangeMax) {
        this.rangeMax = rangeMax;
    }

    public String getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(String searchRequest) {
        this.searchRequest = searchRequest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public boolean isEmpty() {
        if (!regions.isEmpty()) {
            return false;
        }
        if (!categories.isEmpty()) {
            return false;
        }
        if (rangeMin != 0 || rangeMax != 0) {
            return false;
        }
        if (!searchRequest.isEmpty()){
            return false;
        }
        return currentData.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnnouncementPagination that = (AnnouncementPagination) o;

        if (rangeMin != that.rangeMin) return false;
        if (rangeMax != that.rangeMax) return false;
        if (regions != null ? !regions.equals(that.regions) : that.regions != null) return false;
        if (categories != null ? !categories.equals(that.categories) : that.categories != null) return false;
        if (searchRequest != null ? !searchRequest.equals(that.searchRequest) : that.searchRequest != null)
            return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = 31 * (regions != null ? regions.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + rangeMin;
        result = 31 * result + rangeMax;
        result = 31 * result + (searchRequest != null ? searchRequest.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AnnouncementPagination{" +
                "regions=" + regions +
                ", categories=" + categories +
                ", rangeMin=" + rangeMin +
                ", rangeMax=" + rangeMax +
                ", searchRequest='" + searchRequest + '\'' +
                ", status=" + status +
                ", previous=" + previous +
                ", next=" + next +
                ", currentPage=" + currentPage +
                ", currentData=" + (currentData == null ? "empty" : "exists") +
                '}';
    }
}
