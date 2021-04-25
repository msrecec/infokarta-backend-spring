package it.geosolutions.mapstore.service.rasvjeta;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import it.geosolutions.mapstore.utils.search.SearchEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RasvjetaService {

    Optional<RasvjetaDTO> findById(Integer id, Boolean geom);
    EntityListDTO findAll(Boolean geom);
    EntityListDTO findPaginated(Integer page, Boolean geom);
    Optional<RasvjetaDTO> update(RasvjetaPutCommand rasvjetaCommand, Boolean geom);
    EntityListDTO fullSearch(Map<String, Object> params, SearchEntity entity, Integer page, List<SearchEntity> orderedEntities, Boolean geom);


    /**
     * Maps Rasvjeta entity model to DTO. If geom == true, then map with geom else without.
     *
     * @param rasvjeta entity model
     * @param geom
     * @return DTO
     */

    default RasvjetaDTO mapRasvjetaToRasvjetaDTO(Rasvjeta rasvjeta, Boolean geom) {
        if(geom) {
            return new RasvjetaDTO.Builder().geom(rasvjeta.getGeom()).fid(rasvjeta.getFid()).materijal(rasvjeta.getMaterijal()).stanje(rasvjeta.getStanje()).source(rasvjeta.getSource())
                .mjernoMjesto(rasvjeta.getMjernoMjesto()).vod(rasvjeta.getVod()).kategorija(rasvjeta.getKategorija()).vrstaRasvjetnogMjesta(rasvjeta.getVrstaRasvjetnogMjesta())
                .razdjelnik(rasvjeta.getRazdjelnik()).trosilo(rasvjeta.getTrosilo()).vrstaSvjetiljke(rasvjeta.getVrstaSvjetiljke()).brojSvjetiljki(rasvjeta.getBrojSvjetiljki())
                .grlo(rasvjeta.getGrlo()).vrstaStakla(rasvjeta.getVrstaStakla()).polozajKabela(rasvjeta.getPolozajKabela()).godinaIzgradnje(rasvjeta.getGodinaIzgradnje())
                .oznakaUgovora(rasvjeta.getOznakaUgovora()).idHist(rasvjeta.getIdHist()).timeStart(rasvjeta.getTimeStart()).timeEnd(rasvjeta.getTimeEnd())
                .userRole(rasvjeta.getUserRole()).build();
        } else {
            return new RasvjetaDTO.Builder().fid(rasvjeta.getFid()).materijal(rasvjeta.getMaterijal()).stanje(rasvjeta.getStanje()).source(rasvjeta.getSource())
                .mjernoMjesto(rasvjeta.getMjernoMjesto()).vod(rasvjeta.getVod()).kategorija(rasvjeta.getKategorija()).vrstaRasvjetnogMjesta(rasvjeta.getVrstaRasvjetnogMjesta())
                .razdjelnik(rasvjeta.getRazdjelnik()).trosilo(rasvjeta.getTrosilo()).vrstaSvjetiljke(rasvjeta.getVrstaSvjetiljke()).brojSvjetiljki(rasvjeta.getBrojSvjetiljki())
                .grlo(rasvjeta.getGrlo()).vrstaStakla(rasvjeta.getVrstaStakla()).polozajKabela(rasvjeta.getPolozajKabela()).godinaIzgradnje(rasvjeta.getGodinaIzgradnje())
                .oznakaUgovora(rasvjeta.getOznakaUgovora()).idHist(rasvjeta.getIdHist()).timeStart(rasvjeta.getTimeStart()).timeEnd(rasvjeta.getTimeEnd())
                .userRole(rasvjeta.getUserRole()).build();
        }
    }

    /**
     * Maps entity model list to dto list. If geom == true, then map with geom else without.
     *
     * @param rasvjetaList entity list
     * @param geom geometry
     * @return dto list
     */

    default List<RasvjetaDTO> mapRasvjetaListToRasvjetaDTOList(List<Rasvjeta> rasvjetaList, Boolean geom) {
        List<RasvjetaDTO> rasvjetaDTOList = new ArrayList<>();
        for(Rasvjeta rasvjeta : rasvjetaList) {
            rasvjetaDTOList.add(mapRasvjetaToRasvjetaDTO(rasvjeta, geom));
        }
        return rasvjetaDTOList;
    }


    /**
     * Maps command object to entity model
     *
     * @param command input object
     * @return entity model
     */

    default Rasvjeta mapPutCommandToRasvjeta(RasvjetaPutCommand command) {

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
}
