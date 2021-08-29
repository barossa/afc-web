package by.epam.afc.dao;

import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.Dialog;
import by.epam.afc.dao.model.Message;
import by.epam.afc.dao.model.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface DialogDao extends BaseDao<Dialog>{
    List<Dialog> findByUser(User user) throws DaoException;
    Optional<Dialog> findByAnnouncement(Announcement announcement) throws DaoException;
    Optional<Dialog> findByMessage(Message message) throws DaoException;
}
