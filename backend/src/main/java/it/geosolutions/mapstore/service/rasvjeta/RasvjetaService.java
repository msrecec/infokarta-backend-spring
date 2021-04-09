package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.model.Rasvjeta;

import java.util.Optional;

public interface RasvjetaService {
    public Optional<Rasvjeta> findByIdHist(Integer idHist);
}
