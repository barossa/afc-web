package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.model.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.constants.ColumnName.*;

public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRows(ResultSet rs) throws SQLException {
        return Message.getBuilder()
                .id(rs.getInt(MESSAGE_ID))
                .dialogId(rs.getInt(DIALOG_ID))
                .senderId(rs.getInt(SENDER_ID))
                .sentTime(rs.getTimestamp(SENT_TIME).toLocalDateTime())
                .text(rs.getString(TEXT_CONTENT))
                .graphicsContent(rs.getBoolean(GRAPHIC_CONTENT))
                .build();
    }
}
