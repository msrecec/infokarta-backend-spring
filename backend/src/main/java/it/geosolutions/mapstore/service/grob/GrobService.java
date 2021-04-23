package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.grob.GrobPutCommand;

import java.util.Optional;

public interface GrobService {
    public Optional<GrobDTO> findById(Integer id);
    public Optional<GrobDTOWithoutGeom> findByIdWithoutGeom(Integer id);
    public EntityListDTO findAll();
    public EntityListDTO findGroboviByGroblje(String groblje);
    EntityListDTO findPaginated(Integer page);
    Optional<GrobDTO> update(GrobPutCommand grobCommand);

    default GrobDTOWithoutGeom mapGrobToGrobDTOWithoutGeom(Grob grob) {
        return new GrobDTOWithoutGeom(grob.getFid(), grob.getSource(), grob.getSource1(), grob.getSource2(), grob.getSource3(), grob.getSource4(), grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(), grob.getGroblje(), grob.getFk());
    }

    default GrobDTO mapGrobToGrobDTO(Grob grob) {
        return new GrobDTO(grob.getGeom(), grob.getFid(), grob.getSource(), grob.getSource1(), grob.getSource2(), grob.getSource3(), grob.getSource4(), grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(), grob.getGroblje(), grob.getFk());
    }
}
