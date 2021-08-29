package by.epam.afc.dao.impl;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.User;
import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public final class AnnouncementDaoImpl implements AnnouncementDao {
    static final Logger logger = LogManager.getLogger(DaoHolder.class);

    AnnouncementDaoImpl(){
    }

    @Override
    public List<Announcement> findByOwner(User owner) throws DaoException {
        return null;
    }

    @Override
    public List<Announcement> findByCategory(Announcement.Category category) throws DaoException {
        return null;
    }

    @Override
    public List<Announcement> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Announcement> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Announcement> update(Announcement announcement) throws DaoException {
        return null;
    }

    @Override
    public Optional<Announcement> save(Announcement announcement) throws DaoException {
        return null;
    }
}
