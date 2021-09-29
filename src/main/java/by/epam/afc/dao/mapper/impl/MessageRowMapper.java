package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

public class MessageRowMapper implements RowMapper<Message> {
    private static final MessageRowMapper instance = new MessageRowMapper();

    private MessageRowMapper(){}

    public static MessageRowMapper getInstance(){
        return instance;
    }

    @Override
    public Message mapRows(ResultSet rs) throws SQLException {
        Image content = Image.getBuilder()
                .id(rs.getInt(IMAGE_ID))
                .build();

        Message message =  Message.getBuilder()
                .id(rs.getInt(MESSAGE_ID))
                .dialogId(rs.getInt(DIALOG_ID))
                .senderId(rs.getInt(SENDER_ID))
                .sentTime(rs.getTimestamp(SENT_TIME).toLocalDateTime())
                .text(rs.getString(TEXT_CONTENT))
                .graphicsContent(rs.getBoolean(GRAPHIC_CONTENT))
                .image(content)
                .build();
        return message;
    }
}
