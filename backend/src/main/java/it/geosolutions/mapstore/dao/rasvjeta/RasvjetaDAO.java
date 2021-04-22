package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RasvjetaDAO extends DAO<Rasvjeta> {

    public List<Rasvjeta> search(Map<String, Object> params, String entity, Integer page);

    public Integer searchCount(Map<String, Object> params, String entity);

}
