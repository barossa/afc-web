package by.epam.afc.controller.command.pagination;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;

import java.util.List;

public class MyAnnouncementsPagination extends AnnouncementsPagination {
    public MyAnnouncementsPagination(List<Region> regions, List<Category> categories, int rangeMin, int rangeMax, String searchRequest, Announcement.Status status) {
        super(regions, categories, rangeMin, rangeMax, searchRequest, status);
    }

    public MyAnnouncementsPagination(Announcement.Status status) {
        super(status);
    }
}
