package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.constants.ColumnName.*;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRows(ResultSet rs) throws SQLException {
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
                .build();
    }
}
