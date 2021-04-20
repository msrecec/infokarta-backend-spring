package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.grob.GrobPutCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrobServiceImpl implements GrobService {
    @Autowired
    GrobDAO grobDAO;

    @Override
    public Optional<GrobDTO> findById(Integer id) {

        Optional<Grob> grob = grobDAO.findById(id);

        if(grob.isPresent()) {
            return Optional.of(mapToGrobDTO(grob.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public EntityListDTO findAll() {
        List<Grob> grob = grobDAO.findAll();
        Integer count = grobDAO.findCount();

        return mapGrobToEntityListDTO(grob, count);
    }

    @Override
    public Optional<GrobDTOWithoutGeom> findByIdWithoutGeom(Integer id) {

        Optional<Grob> grob = grobDAO.findById(id);

        if(grob.isPresent()) {
            return Optional.of(mapToGrobDTOWithoutGeom(grob.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public EntityListDTO findPaginated(Integer page) {
        List<Grob> grob = grobDAO.findPaginated(page);
        Integer count = grobDAO.findCount();
        return mapGrobToEntityListDTO(grob, count);
    }

    @Override
    public EntityListDTO findGroboviByGroblje(String groblje) {
        List<Grob> grob = grobDAO.getGroboviByGroblje(groblje);
        Integer count = grobDAO.findCount();
        return mapGrobToEntityListDTO(grob, count);
    }

    @Override
    public Optional<GrobDTO> update(GrobPutCommand grobCommand) {
        return Optional.empty();
    }

    private EntityListDTO mapGrobToEntityListDTO(List<Grob> grob, Integer count) {
        return new EntityListDTO(grob, count);
    }

    private GrobDTOWithoutGeom mapToGrobDTOWithoutGeom(Grob grob) {
        return new GrobDTOWithoutGeom(grob.getFid(), grob.getSource(), grob.getSource1(), grob.getSource2(), grob.getSource3(), grob.getSource4(), grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(), grob.getGroblje(), grob.getFk());
    }

    private GrobDTO mapToGrobDTO(Grob grob) {
        return new GrobDTO(grob.getGeom(), grob.getFid(), grob.getSource(), grob.getSource1(), grob.getSource2(), grob.getSource3(), grob.getSource4(), grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(), grob.getGroblje(), grob.getFk());
    }

}
