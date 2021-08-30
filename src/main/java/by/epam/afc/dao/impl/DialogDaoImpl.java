package by.epam.afc.dao.impl;

import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.impl.DialogRowMapper;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.ColumnName.*;
import static by.epam.afc.dao.TableName.*;

public final class DialogDaoImpl implements DialogDao {
    static final Logger logger = LogManager.getLogger(DialogDaoImpl.class);

    private static final String SELECT_ALL_DIALOGS = "SELECT " + DIALOG_ID + "," + TYPE_DESCRIPTION + "," + ANNOUNCEMENT_ID + "," + VISIBLE
            + " FROM " + DIALOGS
            + " INNER JOIN " + DIALOG_TYPES + " ON " + DIALOGS + "." + TYPE_ID + "=" + DIALOG_TYPES + "." + TYPE_ID
            + " INNER JOIN " + USER_DIALOGS + " ON " + DIALOGS + "." + DIALOG_ID + "=" + USER_DIALOGS + "." + DIALOG_ID + ";";

    private static final String SELECT_DIALOG_BY_ID = "SELECT " + DIALOG_ID + "," + TYPE_DESCRIPTION + "," + ANNOUNCEMENT_ID + "," + VISIBLE
            + " FROM " + DIALOGS
            + " INNER JOIN " + DIALOG_TYPES + " ON " + DIALOGS + "." + TYPE_ID + "=" + DIALOG_TYPES + "." + TYPE_ID
            + " INNER JOIN " + USER_DIALOGS + " ON " + DIALOGS + "." + DIALOG_ID + "=" + USER_DIALOGS + "." + DIALOG_ID
            + " WHERE " + DIALOG_ID + "=?;";

    private static final String UPDATE_DIALOG = "UPDATE " + DIALOGS + " SET " + TYPE_ID + "=?," + ANNOUNCEMENT_ID + "=?"
            + " WHERE " + DIALOG_ID + "=?;";

    private static final String UPDATE_USER_DIALOG_INFO = "UPDATE " + USER_DIALOGS + " SET " + VISIBLE + "=?"
            + " WHERE " + DIALOG_ID + "=?;";

    private static final String INSERT_DIALOG = "INSERT INTO " + DIALOGS + "(" + TYPE_ID + "," + ANNOUNCEMENT_ID + ") VALUES(?,?);";

    private static final String INSERT_USER_DIALOG_INFO = "INSERT INTO " + USER_DIALOGS + "(" + USER_ID + "," + DIALOG_ID + "," + VISIBLE + ")"
            + " VALUES(?,?,?);";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<Dialog> findAll() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DIALOGS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Dialog> dialogs = new ArrayList<>();
            DialogRowMapper mapper = new DialogRowMapper();
            while (resultSet.next()) {
                Dialog dialog = mapper.mapRows(resultSet);
                dialogs.add(dialog);
            }
            return dialogs;

        } catch (SQLException e) {
            logger.error("Can't find all dialogs:", e);
            throw new DaoException("Can't find all dialogs", e);
        }
    }

    @Override
    public Optional<Dialog> findById(int id) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_DIALOG_BY_ID)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DialogRowMapper mapper = new DialogRowMapper();
                Dialog dialog = mapper.mapRows(resultSet);
                return Optional.of(dialog);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't find dialog by id:", e);
            throw new DaoException("Can't find dialog by id", e);
        }
    }

    @Override
    public Optional<Dialog> update(Dialog dialog) throws DaoException {
        Optional<Dialog> byId = findById(dialog.getId());
        if (!byId.isPresent()) {
            logger.error("Dialog with id=" + dialog.getId() + " is not presented to update!");
            return Optional.empty();
        }

        try (Connection connection = pool.getConnection();
             PreparedStatement updateDialog = connection.prepareStatement(UPDATE_DIALOG);
             PreparedStatement updateUserDialog = connection.prepareStatement(UPDATE_USER_DIALOG_INFO)) {

            updateDialog.setInt(1, dialog.getType().ordinal() + ENUM_INDEX_DIFFERENCE);
            updateDialog.setInt(2, dialog.getAnnouncementId());
            updateDialog.setInt(3, dialog.getId());
            updateDialog.execute();

            updateUserDialog.setBoolean(1, dialog.isVisible());
            updateUserDialog.setInt(2, dialog.getId());
            updateUserDialog.execute();

            return Optional.of(dialog);

        } catch (SQLException e) {
            logger.error("Can't update dialog with id=" + dialog.getId(), e);
            throw new DaoException("Can't update dialog by id", e);
        }
    }

    @Override
    public Optional<Dialog> save(Dialog dialog) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement insertDialog = connection.prepareStatement(INSERT_DIALOG,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertDialog.setInt(1, dialog.getType().ordinal() + ENUM_INDEX_DIFFERENCE);
            insertDialog.setInt(2, dialog.getAnnouncementId());
            insertDialog.execute();

            ResultSet generatedKeys = insertDialog.getGeneratedKeys();
            if (generatedKeys.next()) {
                dialog.setId(generatedKeys.getInt(ID_KEY));
                return Optional.of(dialog);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new dialog:", e);
            throw new DaoException("Can't save new dialog", e);
        }
    }

    @Override
    public List<Dialog> findByUser(User user) throws DaoException {//// TODO: 8/30/21  
        return null;
    }

    @Override
    public Optional<Dialog> findByAnnouncement(Announcement announcement) throws DaoException { /// TODO: 8/30/21  
        return Optional.empty();
    }

    @Override
    public Optional<Dialog> findByMessage(Message message) throws DaoException {//// TODO: 8/30/21  
        return Optional.empty();
    }

    @Override
    public boolean saveUserDialogInfo(User user, Dialog dialog) throws DaoException { //// TODO: 8/30/21   
        try (Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_USER_DIALOG_INFO)){
            statement.setInt(1, user.getId());
            statement.setInt(2, dialog.getId());
            statement.setBoolean(3, dialog.isVisible());
            return statement.execute();
        }catch (SQLException e){
            logger.error("Can't update user's dialog info:", e);
            throw new DaoException("Can't update user's dialog info", e);
        }
    }
}
