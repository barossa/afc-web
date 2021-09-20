package by.epam.afc.service.impl;

import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.MessageDao;
import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MessageServiceImpl implements MessageService {
    private static final MessageServiceImpl instance = new MessageServiceImpl();
    private final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    private MessageServiceImpl(){}

    public static MessageServiceImpl getInstance(){
        return instance;
    }

    @Override
    public List<Dialog> getUserDialogs(User user) throws ServiceException {
        try{
            DialogDao dialogDao = DaoHolder.getDialogDao();
            return dialogDao.findByUser(user);
        }catch (DaoException e){
            logger.error("Can't load all user dialogs", e);
            throw new ServiceException("Can't load all user dialogs", e);
        }
    }

    @Override
    public List<Message> getDialogMessages(Dialog dialog) throws ServiceException {
        try{
            MessageDao messageDao = DaoHolder.getMessageDao();
            return messageDao.findByDialog(dialog);
        }catch (DaoException e){
            logger.error("Can't load all user dialogs", e);
            throw new ServiceException("Can't load all user dialogs", e);
        }
    }

    @Override
    public Optional<Message> sendMessage(Message message) throws ServiceException {
        DaoHolder.getMessageDao();

        return Optional.empty();
    }
}
