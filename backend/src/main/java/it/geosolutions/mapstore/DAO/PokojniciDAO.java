package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.api.Pokojnik;

import javax.sql.DataSource;
import java.util.List;

public interface PokojniciDAO {

    public void setDataSource(DataSource ds);

    public List<Pokojnik> listPokojnici();

}
