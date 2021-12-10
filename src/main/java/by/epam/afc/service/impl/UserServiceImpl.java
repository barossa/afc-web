package by.epam.afc.service.impl;

import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.util.PasswordCryptor;
import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.epam.afc.dao.entity.User.Role.USER;
import static by.epam.afc.dao.entity.User.Status.DELAYED_REG;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.*;

public class UserServiceImpl implements UserService { //// TODO: 9/28/21 LOAD USERS PROFILE IMAGES 
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(String authField, char[] password) throws ServiceException { //// TODO: 9/2/21 Add validation log/pass
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();

        User user;
        if (validator.validateLogin(authField)) {
            user = User.getBuilder()
                    .login(authField)
                    .build();
        } else if (validator.validateEmail(authField)) {
            user = User.getBuilder()
                    .email(authField)
                    .build();
        } else if (validator.validatePhone(authField)) {
            user = User.getBuilder()
                    .phone(authField)
                    .build();
        } else {
            logger.debug("Can't recognize valid auth field!");
            return Optional.empty();
        }

        if (!validator.validatePassword(String.valueOf(password))) {
            return Optional.empty();
        }

        UserDaoImpl userDao = DaoHolder.getUserDao();
        try {
            Optional<String> optionalHash = userDao.findEncryptedPassword(user);
            if (optionalHash.isPresent()) {
                String hash = optionalHash.get();
                PasswordCryptor cryptor = PasswordCryptor.getInstance();
                boolean verified = cryptor.verify(password, hash.toCharArray());

                if (verified) {
                    Optional<User> optionalUser = userDao.findUniqUser(user);
                    User authenticatedUser = optionalUser.orElseThrow(DaoException::new);
                    initializeProfileImage(authenticatedUser);
                    return Optional.of(authenticatedUser);
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("Can't authenticate user", e);
            throw new ServiceException("Can't authenticate user", e);
        }
    }

    @Override
    public Optional<User> register(Map<String, String> credentialsMap) throws ServiceException {
        CredentialsValidatorImpl credentialsValidator = CredentialsValidatorImpl.getInstance();
        Map<String, String> validatedCredentials = credentialsValidator.validateCredentials(credentialsMap);
        if (validatedCredentials.containsValue("")) {
            return Optional.empty();
        }

        User user = User.getBuilder()
                .firstName(validatedCredentials.get(FIRSTNAME))
                .lastName(validatedCredentials.get(LASTNAME))
                .login(validatedCredentials.get(LOGIN))
                .email(validatedCredentials.get(EMAIL))
                .phone(validatedCredentials.get(PHONE))
                .role(USER)
                .status(DELAYED_REG)
                .build();
        UserDaoImpl userDao = DaoHolder.getUserDao();
        try {
            Optional<User> optionalSaved = userDao.save(user);
            if (!optionalSaved.isPresent()) {
                logger.error("Saved user is empty!");
                throw new ServiceException("UserDao returned empty saved user!");
            }
            User savedUser = optionalSaved.get();
            char[] password = validatedCredentials.get(PASSWORD).toCharArray();
            updatePassword(savedUser, password);
            return Optional.of(savedUser);

        } catch (DaoException e) {
            logger.error("Can't register user", e);
            throw new ServiceException("Can't register user", e);
        }
    }

    @Override
    public void updatePassword(User user, char[] newPassword) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            PasswordCryptor cryptor = PasswordCryptor.getInstance();
            String encrypted = cryptor.encrypt(newPassword);
            userDao.updateUserPassword(user, encrypted);
        } catch (DaoException e) {
            logger.error("can't update user id= " + user.getId() + " password: ", e);
            throw new ServiceException("Can't update user password: ", e);
        }
    }

    @Override
    public boolean findLogin(String login) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            Optional<User> optionalUser = userDao.findByLogin(login);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Can't determine login existing", e);
            throw new ServiceException("Can't determine login existing", e);
        }
    }

    @Override
    public boolean findEmail(String email) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            Optional<User> optionalUser = userDao.findByEmail(email);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Can't determine email existing: ", e);
            throw new ServiceException("Can't determine email existing", e);
        }
    }

    @Override
    public boolean findPhone(String phone) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            Optional<User> optionalUser = userDao.findByPhone(phone);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Can't determine phone existing: ", e);
            throw new ServiceException("Can't determine phone existing", e);
        }
    }

    private void initializeProfileImage(User user) {
        try {
            ImageDao imageDao = DaoHolder.getImageDao();
            Image emptyImage = user.getProfileImage();
            Optional<Image> optionalImage = imageDao.findById(emptyImage.getId());
            Image image = optionalImage.orElseThrow(DaoException::new);
            user.setProfileImage(image);
        } catch (DaoException e) {
            logger.error("Can't initialize user profile image", e);
        }
    }
}