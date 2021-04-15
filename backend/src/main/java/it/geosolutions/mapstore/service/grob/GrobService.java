package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.grob.GrobPutCommand;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;

import java.util.Optional;

public interface GrobService {
    public Optional<Grob> findById(Integer id);
    public EntityListDTO findAll();
    EntityListDTO findPaginated(Integer page);
    Optional<Grob> update(GrobPutCommand grobCommand);
}
