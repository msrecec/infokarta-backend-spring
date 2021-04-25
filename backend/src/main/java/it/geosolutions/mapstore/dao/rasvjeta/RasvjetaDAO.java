package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import it.geosolutions.mapstore.utils.search.SearchEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RasvjetaDAO extends DAO<Rasvjeta> {

    List<Rasvjeta> fullSearch(Map<String, Object> params, SearchEntity entity,
                              Integer page, List<SearchEntity> orderedEntities);

    Integer fullSearchCount(Map<String, Object> params, SearchEntity entity,
                            List<SearchEntity> orderedEntities);

}
