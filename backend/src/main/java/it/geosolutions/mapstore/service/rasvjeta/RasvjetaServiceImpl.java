package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAOImpl;
import it.geosolutions.mapstore.dto.RasvjetaDTO;
import it.geosolutions.mapstore.model.Rasvjeta;

import java.util.Optional;

public class RasvjetaServiceImpl implements RasvjetaService {
    private RasvjetaDAO rasvjetaDAO;

    public RasvjetaServiceImpl() {
        this.rasvjetaDAO = new RasvjetaDAOImpl();
    }

    @Override
    public Optional<Rasvjeta> findByIdHist(Integer idHist) {
        return rasvjetaDAO.findByIdHist(idHist);
    }
}
