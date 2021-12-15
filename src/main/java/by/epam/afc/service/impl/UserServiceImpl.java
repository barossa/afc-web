package by.epam.afc.service.impl;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.UserDao;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.ImageDaoImpl;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.util.ImageHelper;
import by.epam.afc.service.util.PaginationHelper;
import by.epam.afc.service.util.PasswordCryptor;
import by.epam.afc.service.validator.CredentialsValidator;
import by.epam.afc.service.validator.ImageValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import by.epam.afc.service.validator.impl.ImageValidatorImpl;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.dao.entity.User.Role.USER;
import static by.epam.afc.dao.entity.User.Status.*;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.NOT_VALID;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private static final int USERS_ON_PAGE = 15;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> findById(int id) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            return userDao.findById(id);
        } catch (DaoException e) {
            logger.error("Can't find user by id:", e);
            throw new ServiceException("Can't find user by id", e);
        }
    }

    @Override
    public Optional<User> authenticate(String authField, char[] password) throws ServiceException {
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
    public Optional<User> activate(User user) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            Optional<User> optionalUser = userDao.findById(user.getId());
            User oldUser = optionalUser.orElseThrow(DaoException::new);
            oldUser.setStatus(ACTIVE);
            Optional<User> activatedUser = userDao.update(oldUser);
            return activatedUser;
        } catch (DaoException e) {
            logger.error("Can't activate user account:", e);
            throw new ServiceException("Can't activate user account", e);
        }
    }

    @Override
    public Optional<User> updateMyCredentials(Map<String, String> credentials) throws ServiceException {
        try {
            String idParam = credentials.get(ID);
            int id = Integer.parseInt(idParam);
            UserDao userDao = DaoHolder.getUserDao();
            Optional<User> userOptional = userDao.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                CredentialsValidator credentialsValidator = CredentialsValidatorImpl.getInstance();
                ImageValidator imageValidator = ImageValidatorImpl.getInstance();
                Map<String, String> validatedCredentials = credentialsValidator.validateCredentials(credentials);
                renewBaseCredentials(user, validatedCredentials, true);
                String about = credentials.get(ABOUT);
                String image = credentials.get(IMAGE);
                if (credentialsValidator.validateAbout(about)) {
                    user.setAbout(about);
                }
                if (imageValidator.validateImage(image)) {
                    updateUserImage(user, image);
                }
                Optional<User> updatedOptional = userDao.update(user);
                if (updatedOptional.isPresent()) {
                    User updatedUser = updatedOptional.get();
                    initializeProfileImage(updatedUser);
                    return Optional.of(updatedUser);
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("Can't update user credentials:", e);
            throw new ServiceException("Can't update user credentials:", e);
        }
    }

    @Override
    public Optional<User> updateCredentials(Map<String, String> credentials) throws ServiceException {
        try {
            String idParam = credentials.get(ID);
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            CredentialsValidator credentialsValidator = CredentialsValidatorImpl.getInstance();
            boolean idValid = numberValidator.validateNumber(idParam);
            UserDaoImpl userDao = DaoHolder.getUserDao();
            if (idValid) {
                int id = Integer.parseInt(idParam);
                Optional<User> userOptional = userDao.findById(id);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    Map<String, String> validatedCredentials = credentialsValidator.validateCredentials(credentials);
                    renewBaseCredentials(user, validatedCredentials, false);
                    String login = credentials.get(LOGIN);
                    String role = credentials.get(ROLE);
                    String status = credentials.get(STATUS);
                    if (credentialsValidator.validateLogin(login) && !findLogin(login)) {
                        user.setLogin(login);
                    }
                    if (credentialsValidator.validateRole(role)) {
                        user.setRole(User.Role.valueOf(role.toUpperCase()));
                    }
                    if (credentialsValidator.validateStatus(status)) {
                        user.setStatus(User.Status.valueOf(status.toUpperCase()));
                    }
                    Optional<User> updatedOptional = userDao.update(user);
                    if (updatedOptional.isPresent()) {
                        User updatedUser = updatedOptional.get();
                        initializeProfileImage(updatedUser);
                        return Optional.of(updatedUser);
                    }
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("Can't update user credentials:", e);
            throw new ServiceException("Can't update user credentials", e);
        }
    }

    @Override
    public boolean banUser(int id, String reason) throws ServiceException {
        try {
            CredentialsValidator credentialsValidator = CredentialsValidatorImpl.getInstance();
            boolean validAbout = credentialsValidator.validateAbout(reason);
            UserDaoImpl userDao = DaoHolder.getUserDao();
            Optional<User> optionalUser = userDao.findById(id);
            if (!validAbout || !optionalUser.isPresent()) {
                return false;
            }
            User user = optionalUser.get();
            user.setStatus(BANNED);
            user.setAbout(reason);
            Optional<User> bannedUser = userDao.update(user);
            return bannedUser.isPresent();

        } catch (DaoException e) {
            logger.error("Can't ban user by id:", e);
            throw new ServiceException("Can't ban user by id", e);
        }
    }

    @Override
    public boolean updatePassword(User user, char[] newPassword) throws ServiceException {
        try {
            UserDaoImpl userDao = DaoHolder.getUserDao();
            PasswordCryptor cryptor = PasswordCryptor.getInstance();
            String encrypted = cryptor.encrypt(newPassword);
            return userDao.updateUserPassword(user, encrypted);
        } catch (DaoException e) {
            logger.error("Can't update user id= " + user.getId() + " password: ", e);
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

    @Override
    public Pagination<User> findUsers(Map<String, List<String>> parameterMap) throws ServiceException {
        try {
            PaginationHelper paginationHelper = PaginationHelper.getInstance();
            List<String> pages = parameterMap.get(PAGE);
            int page = paginationHelper.findPage(pages);
            UserDaoImpl userDao = DaoHolder.getUserDao();
            List<User> users = userDao.findAll();
            Pagination<User> pagination = paginationHelper.getPage(users, page, USERS_ON_PAGE);
            List<User> paginatedUsers = pagination.getData();
            paginatedUsers.forEach(this::initializeProfileImage);
            return pagination;
        } catch (DaoException e) {
            logger.error("Can't find paginated user's page:", e);
            throw new ServiceException("Can't find paginated user's page", e);
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

    private void renewBaseCredentials(User user, Map<String, String> credentials, boolean verifyEmail) {
        String firstname = credentials.get(FIRSTNAME);
        String lastname = credentials.get(LASTNAME);
        String phone = credentials.get(PHONE);
        String email = credentials.get(EMAIL);
        if (!firstname.equals(NOT_VALID)) {
            user.setFirstname(firstname);
        }
        if (!lastname.equals(NOT_VALID)) {
            user.setLastname(lastname);
        }
        if (!phone.equals(NOT_VALID)) {
            user.setPhone(phone);
        }
        if (!email.equals(NOT_VALID)) {
            String oldEmail = user.getEmail();
            if (!oldEmail.equals(email) && verifyEmail) {
                user.setStatus(DELAYED_REG);
            }
            user.setEmail(email);
        }
    }

    private void updateUserImage(User user, String base64) throws DaoException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();
        ImageHelper imageHelper = ImageHelper.getInstance();
        String fixedImage = imageHelper.fixImage(base64);
        Image image = Image.getBuilder()
                .base64(fixedImage)
                .uploadedByUser(user)
                .uploadData(LocalDateTime.now())
                .build();
        Optional<Image> imageOptional = imageDao.save(image);
        if (imageOptional.isPresent()) {
            Image profileImage = imageOptional.get();
            user.setProfileImage(profileImage);
        } else {
            logger.error("Can't update user profile image: optional is not presented");
        }
    }
}