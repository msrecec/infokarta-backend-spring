package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.Rasvjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RasvjetaServiceImpl implements RasvjetaService {
    @Autowired
    private RasvjetaDAO rasvjetaDAO;

    @Override
    public Optional<Rasvjeta> findById(Integer id) {
        return rasvjetaDAO.findById(id);
    }

    @Override
    public RasvjetaListDTO findAll() {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();
        Integer count = rasvjetaDAO.findCount();
        return mapRasvjetaToRasvjetaListDTO(rasvjeta, count);
    }

    @Override
    public RasvjetaListDTO findPaginated(Integer page) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findPaginated(page);
        Integer count = rasvjetaDAO.findCount();
        return mapRasvjetaToRasvjetaListDTO(rasvjeta, count);
    }

    private RasvjetaListDTO mapRasvjetaToRasvjetaListDTO(List<Rasvjeta> rasvjeta, Integer count) {

        return new RasvjetaListDTO(rasvjeta, count);

    }

}
