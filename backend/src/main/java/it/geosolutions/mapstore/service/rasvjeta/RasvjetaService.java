package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;

import java.util.Map;
import java.util.Optional;

public interface RasvjetaService {

    public Optional<Rasvjeta> findById(Integer id);
    public RasvjetaListDTO findAll();
    RasvjetaListDTO findPaginated(Integer page);
    Optional<Rasvjeta> update(RasvjetaPutCommand rasvjetaCommand);
    RasvjetaListDTO findSearch(Map<String, Object> params, String entity, Integer page);
}
