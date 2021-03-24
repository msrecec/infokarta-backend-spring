package it.geosolutions.mapstore.repository.Groblje;

import it.geosolutions.mapstore.model.Groblje;

import javax.sql.DataSource;
import java.util.List;

public interface GrobljeDAO {
    public void setDataSource(DataSource ds);

    public List<Groblje> listGroblja();
}
