package by.epam.afc.dao.impl;

import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.mapper.impl.ImageRowMapper;
import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.Dialog;
import by.epam.afc.dao.model.Message;
import by.epam.afc.dao.model.User;
import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public final class DialogDaoImpl implements DialogDao {
    static final Logger logger = LogManager.getLogger(ImageRowMapper.class);
    @Override
    public List<Dialog> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Dialog> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Dialog> update(Dialog dialog) throws DaoException {
        return null;
    }

    @Override
    public Optional<Dialog> save(Dialog dialog) throws DaoException {
        return null;
    }

    @Override
    public List<Dialog> findByUser(User user) throws DaoException {
        return null;
    }

    @Override
    public Optional<Dialog> findByAnnouncement(Announcement announcement) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Dialog> findByMessage(Message message) throws DaoException {
        return Optional.empty();
    }
}
