package by.epam.afc.service;

import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Dialog> getUserDialogs(User user) throws ServiceException;
    List<Message> getDialogMessages(Dialog dialog) throws ServiceException;
    Optional<Message> sendMessage(Message message) throws ServiceException;

}
