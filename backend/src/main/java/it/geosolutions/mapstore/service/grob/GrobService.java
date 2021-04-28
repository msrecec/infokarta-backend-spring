package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.grob.GrobPutCommand;

import java.util.Optional;

public interface GrobService {
    Optional<GrobDTO> findById(Integer id);
    Optional<GrobDTO> findByIdWithoutGeom(Integer id);
    EntityListDTO findAll();
    EntityListDTO findGroboviByGroblje(String groblje);
    EntityListDTO findPaginated(Integer page);
    Optional<GrobDTO> update(GrobPutCommand grobCommand);

    default GrobDTO mapGrobToGrobDTOWithoutGeom(Grob grob) {
        return new GrobDTO.Builder().fid(grob.getFid()).source(grob.getSource()).source1(grob.getSource1()).source2(grob.getSource2()).source3(grob.getSource3())
            .source4(grob.getSource4()).source5(grob.getSource5()).source6(grob.getSource6()).source7(grob.getSource7()).RedniBroj(grob.getRedniBroj())
            .Grobnica(grob.getGrobnica()).brojLezaja(grob.getBrojLezaja()).Groblje(grob.getGroblje()).fk(grob.getFk()).build();
    }

    default GrobDTO mapGrobToGrobDTO(Grob grob) {
        return new GrobDTO(grob.getGeom(), grob.getFid(), grob.getSource(), grob.getSource1(), grob.getSource2(), grob.getSource3(), grob.getSource4(), grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(), grob.getGroblje(), grob.getFk());
    }
}
