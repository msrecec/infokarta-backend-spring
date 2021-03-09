package it.geosolutions.mapstore.DAO.Groblje;

import it.geosolutions.mapstore.pojo.Groblje;

import javax.sql.DataSource;
import java.util.List;

public interface GrobljeDAO {
    public void setDataSource(DataSource ds);

    public List<Groblje> listGroblje();
}
