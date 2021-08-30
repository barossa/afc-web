package by.epam.afc.dao.impl;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.impl.AnnouncementRowMapper;
import by.epam.afc.dao.mapper.impl.CategoryRowMapper;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.ColumnName.*;
import static by.epam.afc.dao.TableName.*;

public final class AnnouncementDaoImpl implements AnnouncementDao {
    static final Logger logger = LogManager.getLogger(AnnouncementDaoImpl.class);

    private static final String SELECT_ALL_ANNOUNCEMENTS = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_DESCRIPTION + "," + CATEGORY_DESCRIPTION + "," + ANNOUNCEMENTS + "." + CATEGORY_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + ANNOUNCEMENT_ID + ";";

    private static final String SELECT_ANNOUNCEMENT_BY_ID = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_DESCRIPTION + "," + CATEGORY_DESCRIPTION + "," + ANNOUNCEMENTS + "." + CATEGORY_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + ANNOUNCEMENT_ID
            + " WHERE " + ANNOUNCEMENT_ID + "=?";

    private static final String SELECT_ANNOUNCEMENTS_BY_OWNER_ID = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_DESCRIPTION + "," + CATEGORY_DESCRIPTION + "," + ANNOUNCEMENTS + "." + CATEGORY_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + ANNOUNCEMENT_ID
            + " WHERE " + OWNER_ID + "=?";

    private static final String SELECT_ANNOUNCEMENTS_BY_CATEGORY_ID = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_DESCRIPTION + "," + CATEGORY_DESCRIPTION + "," + ANNOUNCEMENTS + "." + CATEGORY_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + ANNOUNCEMENT_ID
            + " WHERE " + ANNOUNCEMENT_CATEGORY_ID + "=?";

    private static final String UPDATE_ANNOUNCEMENT = "UPDATE  " + ANNOUNCEMENTS + " SET " + OWNER_ID + "=?," + TITLE + "=?," + PRICE + "=?,"
            + PRIMARY_IMAGE + "=?," + DESCRIPTION + "=?," + PUBLICATION_DATE + "=?," + STATUS_ID + "=?," + CATEGORY_ID + "=?"
            + " WHERE " + ANNOUNCEMENT_ID + "=?;";

    private static final String INSERT_ANNOUNCEMENT = "INSERT INTO " + ANNOUNCEMENTS + "(" + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_ID + "," + CATEGORY_ID + ") VALUES(?,?,?,?,?,?,?,?);";

    private static final String SELECT_ALL_CATEGORIES = "SELECT " + CATEGORY_ID + "," + CATEGORY_DESCRIPTION
            + " FROM " + ANNOUNCEMENT_CATEGORIES + ";";

    private static final String SELECT_CATEGORY_BY_DESCRIPTION = "SELECT " + CATEGORY_ID + "," + CATEGORY_DESCRIPTION
            + " FROM " + ANNOUNCEMENT_CATEGORIES
            + " WHERE " + CATEGORY_DESCRIPTION + "=?;";

    private final ConnectionPool pool = ConnectionPool.getInstance();

    AnnouncementDaoImpl() {
    }

    @Override
    public List<Announcement> findByOwner(User owner) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ANNOUNCEMENTS_BY_OWNER_ID)) {
            statement.setInt(1, owner.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Announcement> announcements = new ArrayList<>();
            AnnouncementRowMapper mapper = new AnnouncementRowMapper();
            while (resultSet.next()) {
                Announcement announcement = mapper.mapRows(resultSet);
                announcements.add(announcement);
            }
            return announcements;
        } catch (SQLException e) {
            logger.error("Can't find announcements by id: ", e);
            throw new DaoException("Can't find announcement by if", e);
        }
    }

    @Override
    public List<Announcement> findByCategory(Category category) throws DaoException {
        List<Announcement> announcements = new ArrayList<>();
        Optional<Category> optionalCategory = findCategoryForName(category.getDescription());
        if (!optionalCategory.isPresent()) {
            logger.error("Unknown announcement category \"" + category.getDescription() + "\"");
            return announcements;
        }

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ANNOUNCEMENTS_BY_CATEGORY_ID)
        ) {
            Category foundedCategory = optionalCategory.get();
            statement.setInt(1, foundedCategory.getId());
            ResultSet resultSet = statement.executeQuery();
            AnnouncementRowMapper mapper = new AnnouncementRowMapper();
            while (resultSet.next()) {
                Announcement announcement = mapper.mapRows(resultSet);
                announcements.add(announcement);
            }
            return announcements;
        } catch (SQLException e) {
            logger.error("Can't find announcements by category:", e);
            throw new DaoException("Can't find announcements by category", e);
        }
    }

    @Override
    public List<Category> findAllCategories() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {

            List<Category> categories = new ArrayList<>();
            CategoryRowMapper mapper = new CategoryRowMapper();
            while (resultSet.next()) {
                Category category = mapper.mapRows(resultSet);
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            logger.error("Can't find all categories:", e);
            throw new DaoException("Can't find all categories", e);
        }
    }

    @Override
    public Optional<Category> findCategoryForName(String category) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_DESCRIPTION)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CategoryRowMapper mapper = new CategoryRowMapper();
                Category foundedCategory = mapper.mapRows(resultSet);
                return Optional.of(foundedCategory);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Can't find category by name: ", e);
            throw new DaoException("Can't find category by name", e);
        }
    }

    @Override
    public List<Announcement> findAll() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ANNOUNCEMENTS);
             ResultSet resultSet = statement.executeQuery()) {
            List<Announcement> announcements = new ArrayList<>();
            AnnouncementRowMapper mapper = new AnnouncementRowMapper();
            while (resultSet.next()) {
                Announcement announcement = mapper.mapRows(resultSet);
                announcements.add(announcement);
            }
            return announcements;
        } catch (SQLException e) {
            logger.error("Can't find all announcements: ", e);
            throw new DaoException("Can't find all announcements", e);
        }
    }

    @Override
    public Optional<Announcement> findById(int id) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ANNOUNCEMENT_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AnnouncementRowMapper mapper = new AnnouncementRowMapper();
                Announcement announcement = mapper.mapRows(resultSet);
                return Optional.of(announcement);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Can't find announcement by id: ", e);
            throw new DaoException("Can't find announcement by id", e);
        }
    }

    @Override
    public Optional<Announcement> update(Announcement announcement) throws DaoException {
        Optional<Announcement> byId = findById(announcement.getId());
        if (!byId.isPresent()) {
            logger.error("Announcement by id=" + announcement.getId() + " is not presented to update!");
            return Optional.empty();
        }
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ANNOUNCEMENT)) {
            statement.setInt(1, announcement.getOwnerId());
            statement.setString(2, announcement.getTitle());
            statement.setBigDecimal(3, announcement.getPrice());
            statement.setInt(4, announcement.getPrimaryImageId());
            statement.setString(5, announcement.getDescription());
            statement.setTimestamp(6, Timestamp.valueOf(announcement.getPublicationDate()));
            statement.setInt(7, announcement.getStatus().ordinal() + ENUM_INDEX_DIFFERENCE);
            statement.setInt(8, announcement.getCategory().getId());
            return Optional.of(announcement);
        } catch (SQLException e) {
            logger.error("Can't update announcement by id=" + announcement.getId(), e);
            throw new DaoException("can't update announcement by id", e);
        }
    }

    @Override
    public Optional<Announcement> save(Announcement announcement) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ANNOUNCEMENT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, announcement.getOwnerId());
            statement.setString(2, announcement.getTitle());
            statement.setBigDecimal(3, announcement.getPrice());
            statement.setInt(4, announcement.getPrimaryImageId());
            statement.setString(5, announcement.getDescription());
            statement.setTimestamp(6, Timestamp.valueOf(announcement.getPublicationDate()));
            statement.setInt(7, announcement.getStatus().ordinal() + ENUM_INDEX_DIFFERENCE);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                announcement.setId(generatedKeys.getInt(ID_KEY));
                return Optional.of(announcement);
            } else {
                logger.error("Can't get generated keys from Result Set!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new announcement:", e);
            throw new DaoException("Can't save new announcement", e);
        }
    }
}
