package it.geosolutions.mapstore.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Integer pageSize = 30;

    Optional<T> findById(Integer id);

    List<T> findAll();

    Integer findCount();

    List<T> findPaginated(Integer page);

    void save(T t);

    void update(T t);

}
