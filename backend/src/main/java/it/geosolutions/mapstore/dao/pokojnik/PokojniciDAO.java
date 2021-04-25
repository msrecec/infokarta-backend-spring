package it.geosolutions.mapstore.dao.pokojnik;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PokojniciDAO extends DAO<Pokojnik> {

    public String listPokojnici();

    List<Pokojnik> searchPokojnici(Map<String, Object> params, SearchEntity entity,
                                      Integer page, List<SearchEntity> orderedEntities);

    Integer findJoinedSearchCount(Map<String, Object> params, SearchEntity entity,
                                  List<SearchEntity> orderedEntities);

    public Pokojnik getPokojnikById(Optional<Integer> id);

    public String searchPokojnici(Optional<String> ime,
                                          Optional<String> prezime,
                                          Optional<String> oPocGodinaUkopa,
                                          Optional<String> oKonGodinaUkopa,
                                          Optional<String> oGroblje,
                                          Optional<Integer> oPage
    ) throws UnsupportedEncodingException;

    public List<Pokojnik> getPokojnikByGrobljeFid(Integer grobljeFid);

    public List<String> listColumns();

    public Integer getPokojnikCount();

    public String updatePokojnik(Pokojnik pokojnik);

    public Integer addPokojnik(Pokojnik pokojnik);

    public Integer addPokojnikByGrobljeAndRbr(Pokojnik pokojnik, String groblje, String rbr);

    public Pokojnik getFirstPokojnik();

}
