package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;

import java.util.Map;
import java.util.Optional;

public interface RasvjetaService {

    public Optional<RasvjetaDTO> findById(Integer id);
    public EntityListDTO findAll();
    EntityListDTO findPaginated(Integer page);
    Optional<RasvjetaDTO> update(RasvjetaPutCommand rasvjetaCommand);
    EntityListDTO findSearch(Map<String, Object> params, String entity, Integer page);
}
