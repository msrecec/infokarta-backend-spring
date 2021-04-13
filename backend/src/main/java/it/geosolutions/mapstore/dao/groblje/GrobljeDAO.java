package it.geosolutions.mapstore.dao.groblje;

import it.geosolutions.mapstore.model.groblje.Groblje;

import javax.sql.DataSource;
import java.util.List;

public interface GrobljeDAO {
    public void setDataSource(DataSource ds);

    public List<Groblje> listGroblja();
}
