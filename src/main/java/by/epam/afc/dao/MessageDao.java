package by.epam.afc.dao;

import by.epam.afc.dao.model.Dialog;
import by.epam.afc.dao.model.Message;
import by.epam.afc.exception.DaoException;

import java.util.List;

public interface MessageDao extends BaseDao<Message>{
    List<Message> findByPart(String part) throws DaoException;
    List<Message> findByDialog(Dialog dialog) throws DaoException;
}
