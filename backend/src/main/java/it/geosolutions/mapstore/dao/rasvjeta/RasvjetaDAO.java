package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.model.Rasvjeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface RasvjetaDAO {
    Integer pageSize = 5;

    public void setDataSource(DataSource ds);

    List<Rasvjeta> findAll();

    Optional<Rasvjeta> findById(Integer id);

    List<Rasvjeta> findPaginated(Integer page);

    Integer findCount();
}
