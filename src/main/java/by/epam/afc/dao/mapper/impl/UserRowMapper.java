package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

/**
 * The type User row mapper.
 */
public class UserRowMapper implements RowMapper<User> {
    private static final UserRowMapper instance = new UserRowMapper();

    private UserRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserRowMapper getInstance() {
        return instance;
    }


    /**
     * Map user rows.
     *
     * @param rs the rs
     * @return the user
     * @throws SQLException the sql exception
     */
    @Override
    public User mapRows(ResultSet rs) throws SQLException {
        Image profileImage = Image.getBuilder()
                .id(rs.getInt(PROFILE_IMAGE_ID))
                .build();

        return User.getBuilder()
                .id(rs.getInt(USER_ID))
                .firstName(rs.getString(FIRST_NAME))
                .lastName(rs.getString(LAST_NAME))
                .login(rs.getString(LOGIN))
                .email(rs.getString(EMAIL))
                .phone(rs.getString(PHONE))
                .role(User.Role.valueOf(rs.getString(ROLE_DESCRIPTION)))
                .status(User.Status.valueOf(rs.getString(STATUS_DESCRIPTION)))
                .about(rs.getString(ABOUT))
                .profileImage(profileImage)
                .build();
    }
}
