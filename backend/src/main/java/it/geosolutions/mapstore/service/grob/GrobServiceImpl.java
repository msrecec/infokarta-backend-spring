package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.model.grob.Grob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrobServiceImpl implements GrobService {
    @Autowired
    GrobDAO grobDAO;

    @Override
    public Optional<Grob> findById(Integer id) {
        return grobDAO.findById(id);
    }
}
