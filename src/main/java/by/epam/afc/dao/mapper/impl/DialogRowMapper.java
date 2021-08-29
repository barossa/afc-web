package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.model.Dialog;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.constants.ColumnName.*;

public class DialogRowMapper implements RowMapper<Dialog> {
    @Override
    public Dialog mapRows(ResultSet rs) throws SQLException {
        return Dialog.getBuilder()
                .id(rs.getInt(DIALOG_ID))
                .announcementId(rs.getInt(ANNOUNCEMENT_ID))
                .visible(rs.getBoolean(VISIBLE))
                .type(Dialog.Type.valueOf(rs.getString(TYPE_DESCRIPTION)))
                .build();
    }
}
