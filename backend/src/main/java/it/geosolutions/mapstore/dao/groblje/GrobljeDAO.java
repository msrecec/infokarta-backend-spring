package it.geosolutions.mapstore.dao.groblje;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.groblje.Groblje;

import javax.sql.DataSource;
import java.util.List;

public interface GrobljeDAO extends DAO<Groblje> {

    public List<Groblje> listGroblja();
}
