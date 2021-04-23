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
    public Optional<RasvjetaDTO> findById(Integer id) {

        Optional<Rasvjeta> rasvjeta = rasvjetaDAO.findById(id);
        Optional<RasvjetaDTO> rasvjetaDTO;

        if(rasvjeta.isPresent()) {
            rasvjetaDTO = Optional.of(mapRasvjetaToRasvjetaDTO(rasvjeta.get()));
        } else {
            rasvjetaDTO = Optional.empty();
        }

        return rasvjetaDTO;
    }

    @Override
    public EntityListDTO findAll() {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();
        Integer count = rasvjetaDAO.findCount();
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

    @Override
    public EntityListDTO findPaginated(Integer page) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.findPaginated(page);
        Integer count = rasvjetaDAO.findCount();
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

    @Override
    public Optional<RasvjetaDTO> update(RasvjetaPutCommand rasvjetaCommand) {

        Rasvjeta rasvjeta = rasvjetaDAO.update(mapPutCommandToRasvjeta(rasvjetaCommand)).get();

        if(rasvjeta == null) {
            return Optional.empty();
        }

        return Optional.of(mapRasvjetaToRasvjetaDTO(rasvjeta));
    }

    @Override
    public EntityListDTO findSearch(Map<String, Object> params, String entity, Integer page) {
        List<Rasvjeta> rasvjeta = rasvjetaDAO.search(params, entity, page);
        Integer count = rasvjetaDAO.searchCount(params, entity);
        List<RasvjetaDTO> rasvjetaDTOList = mapRasvjetaListToRasvjetaDTOList(rasvjeta);
        return new EntityListDTO(rasvjetaDTOList, count);
    }

    /**
     * Maps command object to entity model
     *
     * @param command input object
     * @return entity model
     */

    private Rasvjeta mapPutCommandToRasvjeta(RasvjetaPutCommand command) {

        return new Rasvjeta
            .Builder(command.getIdHist())
            .materijal(command.getMaterijal())
            .stanje(command.getStanje())
            .source(command.getSource())
            .mjernoMjesto(command.getMjernoMjesto())
            .vod(command.getVod())
            .kategorija(command.getKategorija())
            .vrstaRasvjetnogMjesta(command.getVrstaRasvjetnogMjesta())
            .razdjelnik(command.getRazdjelnik())
            .trosilo(command.getTrosilo())
            .vrstaSvjetiljke(command.getVrstaSvjetiljke())
            .brojSvjetiljki(command.getBrojSvjetiljki())
            .grlo(command.getGrlo())
            .vrstaStakla(command.getVrstaStakla())
            .polozajKabela(command.getPolozajKabela())
            .godinaIzgradnje(command.getGodinaIzgradnje())
            .oznakaUgovora(command.getOznakaUgovora())
            .fid(command.getFid())
            .timeStart(command.getTimeStart())
            .timeEnd(command.getTimeEnd())
            .userRole(command.getUserRole())
            .build();
    }

    /**
     * Maps Rasvjeta entity model to DTO
     *
     * @param rasvjeta entity model
     * @return DTO
     */

    private RasvjetaDTO mapRasvjetaToRasvjetaDTO(Rasvjeta rasvjeta) {
        return new RasvjetaDTO.Builder().fid(rasvjeta.getFid()).materijal(rasvjeta.getMaterijal()).stanje(rasvjeta.getStanje()).source(rasvjeta.getSource())
            .mjernoMjesto(rasvjeta.getMjernoMjesto()).vod(rasvjeta.getVod()).kategorija(rasvjeta.getKategorija()).vrstaRasvjetnogMjesta(rasvjeta.getVrstaRasvjetnogMjesta())
            .razdjelnik(rasvjeta.getRazdjelnik()).trosilo(rasvjeta.getTrosilo()).vrstaSvjetiljke(rasvjeta.getVrstaSvjetiljke()).brojSvjetiljki(rasvjeta.getBrojSvjetiljki())
            .grlo(rasvjeta.getGrlo()).vrstaStakla(rasvjeta.getVrstaStakla()).polozajKabela(rasvjeta.getPolozajKabela()).godinaIzgradnje(rasvjeta.getGodinaIzgradnje())
            .oznakaUgovora(rasvjeta.getOznakaUgovora()).idHist(rasvjeta.getIdHist()).timeStart(rasvjeta.getTimeStart()).timeEnd(rasvjeta.getTimeEnd())
            .userRole(rasvjeta.getUserRole()).build();
    }

    /**
     * Maps entity model list to dto list
     *
     * @param rasvjetaList entity list
     * @return dto list
     */

    private List<RasvjetaDTO> mapRasvjetaListToRasvjetaDTOList(List<Rasvjeta> rasvjetaList) {
        List<RasvjetaDTO> rasvjetaDTOList = new ArrayList<>();
        for(Rasvjeta rasvjeta : rasvjetaList) {
            rasvjetaDTOList.add(mapRasvjetaToRasvjetaDTO(rasvjeta));
        }
        return rasvjetaDTOList;
    }

}
