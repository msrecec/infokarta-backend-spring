package it.geosolutions.mapstore.DAO.Pokojnici;

import it.geosolutions.mapstore.pojo.Pokojnik;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface PokojniciDAO {

    public void setDataSource(DataSource ds);

    public List<Pokojnik> listPokojnici();

    public Pokojnik getPokojnikById(Optional<Integer> id);

    public List<Pokojnik> getPokojnikByImeOrPrezimeOrPage(Optional<String> ime,
                                                          Optional<String> prezime,
                                                          Optional<String> oPocGodinaUkopa,
                                                          Optional<String> oKonGodinaUkopa,
                                                          Optional<Integer> oPage);

    public List<String> listColumns();

    public Integer getPokojnikCount();

    public boolean addPokojnik(Pokojnik pokojnik);

}
