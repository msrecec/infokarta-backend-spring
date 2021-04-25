package it.geosolutions.mapstore.dao;

import java.util.List;
import java.util.Optional;

/**
 * Represents an abstraction of methods that must be extended by all interfaces in repository/dao layer
 * and it contains pagination constant for sql 'limit' or representation of number of elements in a page
 *
 * @param <T>
 */

public interface DAO<T> {
    Integer pageSize = 30;

    Optional<T> findById(Integer id);

    List<T> findAll();

    Integer findCount();

    List<T> findPaginated(Integer page);

    Optional<T> save(T t);

    Optional<T> update(T t);

}
