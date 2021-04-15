package it.geosolutions.mapstore.dao.pokojnik;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface PokojniciDAO extends DAO<Pokojnik> {

    public void setDataSource(DataSource ds);

    public String listPokojnici();

    public Pokojnik getPokojnikById(Optional<Integer> id);

    public String searchPokojnici(Optional<String> ime,
                                          Optional<String> prezime,
                                          Optional<String> oPocGodinaUkopa,
                                          Optional<String> oKonGodinaUkopa,
                                          Optional<String> oGroblje,
                                          Optional<Integer> oPage
    ) throws UnsupportedEncodingException;

    public List<Pokojnik> getPokojnikByGrobljeFid(Optional<Integer> grobljeFid);

    public List<String> listColumns();

    public Integer getPokojnikCount();

    public String updatePokojnik(Pokojnik pokojnik);

    public Integer addPokojnik(Pokojnik pokojnik);

    public Integer addPokojnikByGrobljeAndRbr(Pokojnik pokojnik, String groblje, String rbr);

    public Pokojnik getFirstPokojnik();

}
