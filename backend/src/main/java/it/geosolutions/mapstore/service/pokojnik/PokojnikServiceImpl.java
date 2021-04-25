package it.geosolutions.mapstore.service.pokojnik;

import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.service.grob.GrobService;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PokojnikServiceImpl implements PokojnikService{
    @Autowired
    PokojniciDAO pokojniciDAO;
    @Autowired
    GrobDAO grobDAO;
    @Autowired
    GrobService grobService;

    @Override
    public EntityListDTO fullSearch(Map<String, Object> params, SearchEntity entity, Integer page, List<SearchEntity> orderedEntities) {
        List<Pokojnik> pokojnici = pokojniciDAO.fullSearch(params, entity, page, orderedEntities);
        Integer count = pokojniciDAO.fullSearchCount(params, entity, orderedEntities);

        if(pokojnici.isEmpty()) {
            return new EntityListDTO(new ArrayList<PokojnikDTO>(), count);
        }

        List<PokojnikDTO> pokojnikDTOList = mapPokojnikListToPokojnikDTOList(pokojnici);

        return new EntityListDTO(pokojnikDTOList, count);
    }

    @Override
    public PokojnikAndGrobDTO getPokojnikAndGrobWithoutGeomByPokojnikFid(Integer fid) {
        PokojnikAndGrobDTO pokojnikAndGrobWithoutGeomDTO;

        Optional<Integer> oFid = Optional.ofNullable(fid);

        Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oFid);

        PokojnikDTO pokojnikDTO = mapPokojnikToPokojnikDTO(pokojnik);

        Grob grob = grobDAO.getGrobByPokojnikFid(fid);

        GrobDTO grobDTO = grobService.mapGrobToGrobDTOWithoutGeom(grob);

        pokojnikAndGrobWithoutGeomDTO = new PokojnikAndGrobDTO(pokojnikDTO, grobDTO);

        return pokojnikAndGrobWithoutGeomDTO;
    }

    @Override
    public PokojnikAndGrobDTO getPokojnikAndGrobByPokojnikFid(Integer fid) {
        PokojnikAndGrobDTO pokojnikAndGrobDTO;

        Optional<Integer> oFid = Optional.ofNullable(fid);

        Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oFid);

        PokojnikDTO pokojnikDTO = mapPokojnikToPokojnikDTO(pokojnik);

        Grob grob = grobDAO.getGrobByPokojnikFid(fid);

        GrobDTO grobDTO = grobService.mapGrobToGrobDTO(grob);

        pokojnikAndGrobDTO = new PokojnikAndGrobDTO(pokojnikDTO, grobDTO);

        return pokojnikAndGrobDTO;
    }
}
