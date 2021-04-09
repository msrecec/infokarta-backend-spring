package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.model.Rasvjeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface RasvjetaDAO {
    public void setDataSource(DataSource ds);

    public List<Rasvjeta> findAll();
    public Optional<Rasvjeta> findByIdHist(Integer idHist);
}
