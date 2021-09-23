package by.epam.afc.service.impl;

import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.MessageDao;
import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.DialogDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.MessageService;
import by.epam.afc.service.util.EntityChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.entity.BaseEntity.UNDEFINED_ID;
import static by.epam.afc.dao.entity.Dialog.Type;
import static by.epam.afc.dao.entity.Dialog.Type.*;

public class MessageServiceImpl implements MessageService {
    private static final MessageServiceImpl instance = new MessageServiceImpl();
    private final Logger logger = LogManager.getLogger(MessageServiceImpl.class);

    private MessageServiceImpl() {
    }

    public static MessageServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Dialog> getUserDialogs(User user) throws ServiceException {
        try {
            DialogDao dialogDao = DaoHolder.getDialogDao();
            return dialogDao.findByUser(user);
        } catch (DaoException e) {
            logger.error("Can't load all user dialogs", e);
            throw new ServiceException("Can't load all user dialogs", e);
        }
    }

    @Override
    public List<Message> getDialogMessages(Dialog dialog) throws ServiceException {
        try {
            MessageDao messageDao = DaoHolder.getMessageDao();
            return messageDao.findByDialog(dialog);
        } catch (DaoException e) {
            logger.error("Can't load all user dialogs", e);
            throw new ServiceException("Can't load all user dialogs", e);
        }
    }

    @Override
    public Optional<Message> sendMessage(Message message) throws ServiceException {
        if(message.getDialogId() == UNDEFINED_ID){
            return Optional.empty();
        }

        try {
            DialogDaoImpl dialogDao = DaoHolder.getDialogDao();
            Optional<Dialog> optionalDialog = dialogDao.findById(message.getDialogId());
            if(optionalDialog.isPresent()){
                MessageDao messageDao = DaoHolder.getMessageDao();
                return messageDao.save(message);
            }else{
                logger.warn("Can't sent message to unknown dialog!");
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Can't send message", e);
            throw new ServiceException("Can't send message", e);
        }
    }

    @Override
    public Optional<Dialog> getPrivateDialog(User first, User second) throws ServiceException {
        try {
            DialogDao dialogDao = DaoHolder.getDialogDao();
            List<Dialog> firstUserDialogs = dialogDao.findByUser(first);
            List<Dialog> secondUserDialogs = dialogDao.findByUser(second);

            return findCrossingsDialog(firstUserDialogs, secondUserDialogs, PRIVATE);
        } catch (DaoException e) {
            logger.error("Can't load private dialog", e);
            throw new ServiceException("Can't load private dialog", e);
        }
    }

    @Override
    public Optional<Dialog> getAnnouncementDialog(User first, User second) throws ServiceException {
        try {
            DialogDao dialogDao = DaoHolder.getDialogDao();
            List<Dialog> firstUserDialogs = dialogDao.findByUser(first);
            List<Dialog> secondUserDialogs = dialogDao.findByUser(second);

            return findCrossingsDialog(firstUserDialogs, secondUserDialogs, ANNOUNCEMENT);
        } catch (DaoException e) {
            logger.error("Can't load announcement dialog", e);
            throw new ServiceException("Can't load announcement dialog", e);
        }
    }

    private Optional<Dialog> findCrossingsDialog(List<Dialog> first, List<Dialog> second, Type type){
        for (Dialog dialog : first) {
            if (dialog.getType() == type) {
                if(second.contains(dialog)){
                    return Optional.of(dialog);
                }
            }
        }
        return Optional.empty();
    }
}
