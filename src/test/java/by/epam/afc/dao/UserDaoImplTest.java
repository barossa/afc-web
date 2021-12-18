package by.epam.afc.dao;

import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {
    private List<User> users;
    private User user;

    @Mock
    private UserDaoImpl userDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(userDao);
        users = new ArrayList<>();
        user = User.getBuilder()
                .id(1)
                .build();
        users.add(user);
    }

    @Test
    public void findAllTest() throws DaoException {
        when(userDao.findAll()).thenReturn(users);
        List<User> allUsers = userDao.findAll();
        Assertions.assertEquals(users, allUsers);
    }

    @Test
    public void findByIdTest() throws DaoException {
        when(userDao.findById(anyInt())).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.findById(1);
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void updateTest() throws DaoException {
        when(userDao.update(any(User.class))).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.update(user);
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void saveTest() throws DaoException {
        when(userDao.save(any(User.class))).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.save(user);
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void findEncryptedPassword() throws DaoException {
        when(userDao.findEncryptedPassword(any(User.class))).thenReturn(Optional.of("encrypted password"));
        Optional<String> passwordOptional = userDao.findEncryptedPassword(user);
        Assertions.assertTrue(passwordOptional.isPresent());
    }

    @Test
    public void findUniqUserTest() throws DaoException {
        when(userDao.findUniqUser(user)).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.findUniqUser(user);
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void findByLogin() throws DaoException {
        when(userDao.findByLogin(anyString())).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.findByLogin("admin");
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void findUserByEmail() throws DaoException {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.findByEmail("admin@mail.ru");
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void findUserByPhone() throws DaoException {
        when(userDao.findByPhone(anyString())).thenReturn(Optional.of(user));
        Optional<User> userOptional = userDao.findByPhone("375294554545");
        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    public void updateUserPasswordTest() throws DaoException {
        when(userDao.updateUserPassword(any(User.class), anyString())).thenReturn(true);
        boolean actual = userDao.updateUserPassword(user, "encrypted password");
        Assertions.assertTrue(actual);
    }
}
