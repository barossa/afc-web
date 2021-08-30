package by.epam.afc.dao;

import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    int ID_KEY = 1;

    List<T> findAll() throws DaoException;
    Optional<T> findById(int id) throws DaoException;
    Optional<T> update(T t) throws DaoException;
    Optional<T> save(T t) throws DaoException;

}
