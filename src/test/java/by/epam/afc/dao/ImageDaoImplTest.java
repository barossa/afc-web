package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.impl.ImageDaoImpl;
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
public class ImageDaoImplTest {
    @Mock
    private ImageDaoImpl imageDao;
    private List<Image> images;
    private Announcement announcement;
    private Image image;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(imageDao);
        images = new ArrayList<>();
        image = Image.getBuilder()
                .id(1)
                .base64("some data")
                .build();
        images.add(image);
        announcement = Announcement.getBuilder()
                .id(1)
                .images(images)
                .build();
    }

    @Test
    public void findAllTest() throws DaoException {
        when(imageDao.findAll()).thenReturn(images);
        List<Image> allImages = imageDao.findAll();
        Assertions.assertEquals(images, allImages);
    }

    @Test
    public void findByIdTest() throws DaoException {
        when(imageDao.findById(anyInt())).thenReturn(Optional.of(image));
        Optional<Image> imageOptional = imageDao.findById(1);
        Assertions.assertTrue(imageOptional.isPresent());
    }

    @Test
    public void updateTest() throws DaoException {
        when(imageDao.update(any(Image.class))).thenReturn(Optional.of(image));
        Optional<Image> imageOptional = imageDao.update(image);
        Assertions.assertTrue(imageOptional.isPresent());
    }

    @Test
    public void saveTest() throws DaoException {
        when(imageDao.save(any(Image.class))).thenReturn(Optional.of(image));
        Optional<Image> imageOptional = imageDao.save(image);
        Assertions.assertTrue(imageOptional.isPresent());
    }

    @Test
    public void findByAnnouncementTest() throws DaoException {
        when(imageDao.findByAnnouncement(any(Announcement.class))).thenReturn(images);
        List<Image> announcementImages = imageDao.findByAnnouncement(announcement);
        Assertions.assertFalse(announcementImages.isEmpty());
    }

    @Test
    public void saveAnnouncementImagesTest() throws DaoException {
        when(imageDao.saveAnnouncementImages(any(Announcement.class))).thenReturn(images);
        List<Image> announcementImages = imageDao.saveAnnouncementImages(announcement);
        Assertions.assertEquals(images, announcementImages);
    }

}
