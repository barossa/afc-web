package by.epam.afc.dao.impl;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.impl.AnnouncementRowMapper;
import by.epam.afc.dao.mapper.impl.CategoryRowMapper;
import by.epam.afc.dao.mapper.impl.RegionRowMapper;
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
    private static final Logger logger = LogManager.getLogger(AnnouncementDaoImpl.class);

    private static final String SELECT_ALL_ANNOUNCEMENTS = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE_NUMBER + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + ANNOUNCEMENTS + "." + STATUS_ID + "," + STATUS_DESCRIPTION + ","
            + CATEGORY_DESCRIPTION + "," + REGION_NAME + "," + ANNOUNCEMENTS + "." + CATEGORY_ID + "," + ANNOUNCEMENTS + "." + REGION_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + REGIONS + " ON " + ANNOUNCEMENTS + "." + REGION_ID + "=" + REGIONS + "." + REGION_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + CATEGORY_ID + ";";

    private static final String SELECT_ANNOUNCEMENT_BY_ID = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE_NUMBER + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + ANNOUNCEMENTS + "." + STATUS_ID + "," + STATUS_DESCRIPTION + ","
            + CATEGORY_DESCRIPTION + "," + REGION_NAME + "," + ANNOUNCEMENTS + "." + CATEGORY_ID + "," + ANNOUNCEMENTS + "." + REGION_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + REGIONS + " ON " + ANNOUNCEMENTS + "." + REGION_ID + "=" + REGIONS + "." + REGION_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + CATEGORY_ID
            + " WHERE " + ANNOUNCEMENT_ID + "=?;";

    private static final String SELECT_ANNOUNCEMENTS_BY_OWNER_ID = "SELECT " + ANNOUNCEMENT_ID + "," + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE_NUMBER + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + ANNOUNCEMENTS + "." + STATUS_ID + "," + STATUS_DESCRIPTION + ","
            + CATEGORY_DESCRIPTION + "," + REGION_NAME + "," + ANNOUNCEMENTS + "." + CATEGORY_ID + "," + ANNOUNCEMENTS + "." + REGION_ID
            + " FROM " + ANNOUNCEMENTS
            + " JOIN " + ANNOUNCEMENT_STATUSES + " ON " + ANNOUNCEMENTS + "." + STATUS_ID + "=" + ANNOUNCEMENT_STATUSES + "." + STATUS_ID
            + " JOIN " + REGIONS + " ON " + ANNOUNCEMENTS + "." + REGION_ID + "=" + REGIONS + "." + REGION_ID
            + " JOIN " + ANNOUNCEMENT_CATEGORIES + " ON " + ANNOUNCEMENTS + "." + CATEGORY_ID + "=" + ANNOUNCEMENT_CATEGORIES + "." + CATEGORY_ID
            + " WHERE " + OWNER_ID + "=?;";

    private static final String UPDATE_ANNOUNCEMENT = "UPDATE  " + ANNOUNCEMENTS + " SET " + OWNER_ID + "=?," + TITLE + "=?," + PRICE + "=?,"
            + PRIMARY_IMAGE_NUMBER + "=?," + DESCRIPTION + "=?," + PUBLICATION_DATE + "=?," + STATUS_ID + "=?," + CATEGORY_ID + "=?," + REGION_ID + "=?"
            + " WHERE " + ANNOUNCEMENT_ID + "=?;";

    private static final String INSERT_ANNOUNCEMENT = "INSERT INTO " + ANNOUNCEMENTS + "(" + OWNER_ID + "," + TITLE + "," + PRICE + ","
            + PRIMARY_IMAGE_NUMBER + "," + DESCRIPTION + "," + PUBLICATION_DATE + "," + STATUS_ID + "," + CATEGORY_ID + ") VALUES(?,?,?,?,?,?,?,?);";

    private static final String SELECT_ALL_CATEGORIES = "SELECT " + CATEGORY_ID + "," + CATEGORY_DESCRIPTION
            + " FROM " + ANNOUNCEMENT_CATEGORIES
            + " ORDER BY " + CATEGORY_ID + ";";

    private static final String SELECT_CATEGORY_BY_DESCRIPTION = "SELECT " + CATEGORY_ID + "," + CATEGORY_DESCRIPTION
            + " FROM " + ANNOUNCEMENT_CATEGORIES
            + " WHERE " + CATEGORY_DESCRIPTION + "=?;";

    private static final String SELECT_CATEGORY_BY_ID = "SELECT " + CATEGORY_ID + "," + CATEGORY_DESCRIPTION
            + " FROM " + ANNOUNCEMENT_CATEGORIES
            + " WHERE " + CATEGORY_ID + "=?;";

    private static final String SELECT_ALL_REGIONS = "SELECT " + REGION_ID + ", " + REGION_NAME + " FROM " + REGIONS
            + " ORDER BY " + REGION_ID + ";";

    private static final String SELECT_REGION_BY_ID = "SELECT " + REGION_ID + ", " + REGION_NAME + " FROM " + REGIONS
            + " WHERE " + REGION_ID + "=?"
            + " ORDER BY " + REGION_ID + ";";

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
            AnnouncementRowMapper mapper = AnnouncementRowMapper.getInstance();
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
    public List<Category> findAllCategories() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {

            List<Category> categories = new ArrayList<>();
            CategoryRowMapper mapper = CategoryRowMapper.getInstance();
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
    public List<Region> findAllRegions() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REGIONS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Region> regions = new ArrayList<>();
            RegionRowMapper mapper = RegionRowMapper.getInstance();
            while (resultSet.next()) {
                Region region = mapper.mapRows(resultSet);
                regions.add(region);
            }
            return regions;

        } catch (SQLException e) {
            logger.error("Can't find all regions", e);
            throw new DaoException("Can't find all regions", e);
        }
    }

    @Override
    public Optional<Region> findRegion(int id) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REGION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                RegionRowMapper mapper = RegionRowMapper.getInstance();
                Region foundedRegion = mapper.mapRows(resultSet);
                return Optional.of(foundedRegion);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't find region by id: ", e);
            throw new DaoException("Can't find region by id", e);
        }
    }

    @Override
    public Optional<Category> findCategory(String category) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_DESCRIPTION)) {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CategoryRowMapper mapper = CategoryRowMapper.getInstance();
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
    public Optional<Category> findCategory(int id) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CategoryRowMapper mapper = CategoryRowMapper.getInstance();
                Category foundedCategory = mapper.mapRows(resultSet);
                return Optional.of(foundedCategory);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't find category by id: ", e);
            throw new DaoException("Can't find category by id", e);
        }
    }

    @Override
    public List<Announcement> findAll() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ANNOUNCEMENTS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Announcement> announcements = new ArrayList<>();
            AnnouncementRowMapper mapper = AnnouncementRowMapper.getInstance();
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
                AnnouncementRowMapper mapper = AnnouncementRowMapper.getInstance();
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
            fillStatement(statement, announcement);
            statement.setInt(9, announcement.getId());
            statement.setInt(10, announcement.getRegion().getId());
            statement.execute();
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
            fillStatement(statement, announcement);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int announcementId = generatedKeys.getInt(ID_KEY);
                return findById(announcementId);
            } else {
                logger.error("Can't get generated keys from Result Set!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new announcement:", e);
            throw new DaoException("Can't save new announcement", e);
        }
    }

    private void fillStatement(PreparedStatement statement, Announcement announcement) throws SQLException {
        statement.setInt(1, announcement.getOwner().getId());
        statement.setString(2, announcement.getTitle());
        statement.setBigDecimal(3, announcement.getPrice());
        statement.setInt(4, announcement.getPrimaryImageNumber());
        statement.setString(5, announcement.getDescription());
        statement.setTimestamp(6, Timestamp.valueOf(announcement.getPublicationDate()));
        statement.setInt(7, announcement.getStatus().ordinal());
        statement.setInt(8, announcement.getCategory().getId());
    }
}
