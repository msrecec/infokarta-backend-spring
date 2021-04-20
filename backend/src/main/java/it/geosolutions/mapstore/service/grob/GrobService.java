package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.grob.GrobPutCommand;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;

import java.util.Optional;

public interface GrobService {
    public Optional<GrobDTO> findById(Integer id);
    public Optional<GrobDTOWithoutGeom> findByIdWithoutGeom(Integer id);
    public EntityListDTO findAll();
    public EntityListDTO findGroboviByGroblje(String groblje);
    EntityListDTO findPaginated(Integer page);
    Optional<GrobDTO> update(GrobPutCommand grobCommand);
}
