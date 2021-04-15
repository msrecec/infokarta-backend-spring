package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
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
    public Optional<Grob> findById(Integer id) {
        return grobDAO.findById(id);
    }

    @Override
    public EntityListDTO findAll() {
        List<Grob> grob = grobDAO.findAll();
        Integer count = grobDAO.findCount();

        return mapGrobToEntityListDTO(grob, count);
    }

    @Override
    public EntityListDTO findPaginated(Integer page) {
        List<Grob> grob = grobDAO.findPaginated(page);
        Integer count = grobDAO.findCount();
        return mapGrobToEntityListDTO(grob, count);
    }

    @Override
    public Optional<Grob> update(GrobPutCommand grobCommand) {
        return Optional.empty();
    }

    private EntityListDTO mapGrobToEntityListDTO(List<Grob> grob, Integer count) {
        return new EntityListDTO(grob, count);
    }

}
