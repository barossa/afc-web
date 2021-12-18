package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnnouncementDaoImplTest {
    private List<Announcement> announcements;
    private List<Category> categories;
    private List<Region> regions;
    private Announcement announcement;
    private Category category;
    private Region region;
    private User user;

    @Mock
    private AnnouncementDaoImpl announcementDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(announcementDao);
        user = User.getBuilder()
                .id(1)
                .build();
        announcements = new ArrayList<>();
        regions = new ArrayList<>();
        categories = new ArrayList<>();
        announcement = Announcement.getBuilder()
                .id(1)
                .owner(user)
                .build();
        announcements.add(announcement);
        region = new Region(1);
        category = new Category(1);
        regions.add(region);
        categories.add(category);
    }

    @Test
    public void findAllTest() throws DaoException {
        when(announcementDao.findAll()).thenReturn(announcements);
        List<Announcement> allAnnouncements = announcementDao.findAll();
        Assertions.assertEquals(announcements, allAnnouncements);
    }

    @Test
    public void findByIdTest() throws DaoException {
        when(announcementDao.findById(anyInt())).thenReturn(Optional.of(announcement));
        Optional<Announcement> announcementOptional = announcementDao.findById(1);
        Assertions.assertTrue(announcementOptional.isPresent());
    }

    @Test
    public void updateTest() throws DaoException {
        when(announcementDao.update(any(Announcement.class))).thenReturn(Optional.of(announcement));
        Optional<Announcement> announcementOptional = announcementDao.update(announcement);
        Assertions.assertTrue(announcementOptional.isPresent());
    }

    @Test
    public void saveTest() throws DaoException {
        when(announcementDao.save(any(Announcement.class))).thenReturn(Optional.of(announcement));
        Optional<Announcement> announcementOptional = announcementDao.save(announcement);
        Assertions.assertTrue(announcementOptional.isPresent());
    }

    @Test
    public void findByOwnerTest() throws DaoException {
        when(announcementDao.findByOwner(any(User.class))).thenReturn(announcements);
        List<Announcement> userAnnouncements = announcementDao.findByOwner(user);
        Assertions.assertEquals(announcements, userAnnouncements);
    }

    @Test
    public void findAllCategoriesTest() throws DaoException {
        when(announcementDao.findAllCategories()).thenReturn(categories);
        List<Category> allCategories = announcementDao.findAllCategories();
        Assertions.assertEquals(categories, allCategories);
    }

    @Test
    public void findAllRegionTest() throws DaoException {
        when(announcementDao.findAllRegions()).thenReturn(regions);
        List<Region> allRegions = announcementDao.findAllRegions();
        Assertions.assertEquals(regions, allRegions);
    }

    @Test
    public void findCategoryTest() throws DaoException {
        when(announcementDao.findCategory(anyInt())).thenReturn(Optional.of(category));
        Optional<Category> categoryOptional = announcementDao.findCategory(1);
        Assertions.assertTrue(categoryOptional.isPresent());
    }

    @Test
    public void findRegionTest() throws DaoException {
        when(announcementDao.findRegion(anyInt())).thenReturn(Optional.of(region));
        Optional<Region> regionOptional = announcementDao.findRegion(1);
        Assertions.assertTrue(regionOptional.isPresent());
    }

}
