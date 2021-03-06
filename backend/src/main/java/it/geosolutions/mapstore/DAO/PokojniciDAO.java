package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.pojo.Pokojnik;

import javax.sql.DataSource;
import java.util.List;

public interface PokojniciDAO {

    public void setDataSource(DataSource ds);

    public List<Pokojnik> listPokojnici();

    public Pokojnik getPokojnik(Integer id);

    public List<String> listColumns();

    public List<Pokojnik> listPage(Integer pageNum);

    public boolean addPokojnik(Pokojnik pokojnik);

}
