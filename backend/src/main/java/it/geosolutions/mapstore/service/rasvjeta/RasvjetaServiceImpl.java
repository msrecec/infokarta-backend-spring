package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RasvjetaServiceImpl implements RasvjetaService {
    @Autowired
    private RasvjetaDAO rasvjetaDAO;

    /**
     * Finds entity model and maps it to DTO and returns it to application layer (Controller)
     *
     * @param id
     * @return RasvjetaDTO
     */

    @Override
    public Optional<RasvjetaDTO> findById(Integer id, Boolean geom) {

        Optional<Rasvjeta> rasvjeta = rasvjetaDAO.findById(id);
        Optional<RasvjetaDTO> rasvjetaDTO;

        if(rasvjeta.isPresent()) {
            rasvjetaDTO = Optional.of(mapRasvjetaToRasvjetaDTO(rasvjeta.get(), geom));
        } else {
            rasvjetaDTO = Optional.empty();
        }

        return rasvjetaDTO;
    }

    @Override
    public EntityListDTO findAll(Boolean geom) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();
        Integer count = rasvjetaDAO.findCount();
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta, geom);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

    @Override
    public EntityListDTO findPaginated(Integer page, Boolean geom) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findPaginated(page);
        Integer count = rasvjetaDAO.findCount();
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta, geom);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

    @Override
    public Optional<RasvjetaDTO> update(RasvjetaPutCommand rasvjetaCommand, Boolean geom) {

        Rasvjeta rasvjeta = rasvjetaDAO.update(mapPutCommandToRasvjeta(rasvjetaCommand)).get();

        if(rasvjeta == null) {
            return Optional.empty();
        }

        return Optional.of(mapRasvjetaToRasvjetaDTO(rasvjeta, geom));
    }

    @Override
    public EntityListDTO findSearch(Map<String, Object> params, String entity, Integer page, Boolean geom) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.search(params, entity, page);
        Integer count = rasvjetaDAO.searchCount(params, entity);
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta, geom);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

}
