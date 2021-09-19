package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface DialogDao extends BaseDao<Dialog>{
    List<Dialog> findByUser(User user) throws DaoException;
    List<Dialog> findByAnnouncement(Announcement announcement) throws DaoException;
    Optional<Dialog> findByMessage(Message message) throws DaoException;
    boolean saveUserDialogInfo(User user, Dialog dialog) throws DaoException;

}
