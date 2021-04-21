package it.geosolutions.mapstore.service.pokojnik;

import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobWithoutGeomDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.service.grob.GrobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PokojnikServiceImpl implements PokojnikService{
    @Autowired
    PokojniciDAO pojniciDAO;
    @Autowired
    GrobDAO grobDAO;
    @Autowired
    GrobService grobService;

    @Override
    public PokojnikAndGrobWithoutGeomDTO getPokojnikAndGrobWithoutGeomByPokojnikFid(Integer fid) {
        PokojnikAndGrobWithoutGeomDTO pokojnikAndGrobWithoutGeomDTO;

        Optional<Integer> oFid = Optional.ofNullable(fid);

        Pokojnik pokojnik = pojniciDAO.getPokojnikById(oFid);

        PokojnikDTO pokojnikDTO = mapPokojnikToPokojnikDTO(pokojnik);

        Grob grob = grobDAO.getGrobByPokojnikFid(fid);

        GrobDTOWithoutGeom grobDTOWithoutGeom = grobService.mapGrobToGrobDTOWithoutGeom(grob);

        pokojnikAndGrobWithoutGeomDTO = new PokojnikAndGrobWithoutGeomDTO(pokojnikDTO, grobDTOWithoutGeom);

        return pokojnikAndGrobWithoutGeomDTO;
    }

    @Override
    public PokojnikAndGrobDTO getPokojnikAndGrobByPokojnikFid(Integer fid) {
        PokojnikAndGrobDTO pokojnikAndGrobDTO;

        Optional<Integer> oFid = Optional.ofNullable(fid);

        Pokojnik pokojnik = pojniciDAO.getPokojnikById(oFid);

        PokojnikDTO pokojnikDTO = mapPokojnikToPokojnikDTO(pokojnik);

        Grob grob = grobDAO.getGrobByPokojnikFid(fid);

        GrobDTO grobDTO = grobService.mapGrobToGrobDTO(grob);

        pokojnikAndGrobDTO = new PokojnikAndGrobDTO(pokojnikDTO, grobDTO);

        return pokojnikAndGrobDTO;
    }
}
