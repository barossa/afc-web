package by.epam.afc.dao;

import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T> {
    /**
     * The constant ID_KEY.
     */
    int ID_KEY = 1;

    /**
     * Find all entities list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find entity by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(int id) throws DaoException;

    /**
     * Update entity optional.
     *
     * @param t the t
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> update(T t) throws DaoException;

    /**
     * Save entity optional.
     *
     * @param t the t
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> save(T t) throws DaoException;

}
