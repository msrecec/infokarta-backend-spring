package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.model.Rasvjeta;

import javax.sql.DataSource;
import java.util.List;

public interface RasvjetaDAO {
    public void setDataSource(DataSource ds);

    public List<Rasvjeta> findAll();
    public Rasvjeta findByIdHist(Integer idHist);
}
