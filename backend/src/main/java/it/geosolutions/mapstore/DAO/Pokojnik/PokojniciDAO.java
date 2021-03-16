package it.geosolutions.mapstore.DAO.Pokojnik;

import it.geosolutions.mapstore.pojo.Pokojnik;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface PokojniciDAO {

    public void setDataSource(DataSource ds);

    public String listPokojnici();

    public Pokojnik getPokojnikById(Optional<Integer> id);

    public String searchPokojnici(Optional<String> ime,
                                          Optional<String> prezime,
                                          Optional<String> oPocGodinaUkopa,
                                          Optional<String> oKonGodinaUkopa,
                                          Optional<String> oGroblje,
                                          Optional<Integer> oPage);

    public List<String> listColumns();

    public Integer getPokojnikCount();

    public String updatePokojnik(Pokojnik pokojnik);

    public String addPokojnik(Pokojnik pokojnik);

}
