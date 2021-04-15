package it.geosolutions.mapstore.service.grob;

import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;

import java.util.Optional;

public interface GrobService {
    public Optional<Grob> findById(Integer id);
}
