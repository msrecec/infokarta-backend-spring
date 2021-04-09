package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.Rasvjeta;

import java.util.List;
import java.util.Optional;

public interface RasvjetaService {

    public Optional<Rasvjeta> findById(Integer id);
    public RasvjetaListDTO findAll();
    RasvjetaListDTO findPaginated(Integer page);
}
