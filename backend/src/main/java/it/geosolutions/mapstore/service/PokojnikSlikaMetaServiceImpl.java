package it.geosolutions.mapstore.service;

import it.geosolutions.mapstore.DAO.PokojnikSlikaMeta.PokojnikSlikaMetaDAO;
import it.geosolutions.mapstore.DAO.PokojnikSlikaMeta.PokojnikSlikaMetaDAOImpl;
import it.geosolutions.mapstore.dto.PokojnikSlikaMetaDTO;
import it.geosolutions.mapstore.model.PokojnikSlikaMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PokojnikSlikaMetaServiceImpl implements PokojnikSlikaMetaService {

    private PokojnikSlikaMetaDAO pokojnikSlikaMetaDAO;

    public PokojnikSlikaMetaServiceImpl() {
        this.pokojnikSlikaMetaDAO = new PokojnikSlikaMetaDAOImpl();
    }

    @Override
    public PokojnikSlikaMetaDTO getSlikaMetaByFid(Integer fid) {

        Optional<PokojnikSlikaMeta> oPokojnikSlikaMeta = pokojnikSlikaMetaDAO.getSlikaMetaByFid(fid);

        if(oPokojnikSlikaMeta.isPresent()) {
            return mapPokojnikSlikaMetaToDTO(oPokojnikSlikaMeta.get());
        } else {
            return null;
        }
    }

    @Override
    public List<PokojnikSlikaMetaDTO> getSlikaMetaByPokojnikFid(Integer fid) {
        pokojnikSlikaMetaDAO.getSlikaMetaByPokojnikFid(fid);
        List<PokojnikSlikaMetaDTO> pokojnikSlikaMetaDTOList = new ArrayList<>();
        List<PokojnikSlikaMeta> pokojnikSlikaMetaList = pokojnikSlikaMetaDAO.getSlikaMetaByPokojnikFid(fid);

        for(PokojnikSlikaMeta p : pokojnikSlikaMetaList) {
            pokojnikSlikaMetaDTOList.add(mapPokojnikSlikaMetaToDTO(p));
        }

        return pokojnikSlikaMetaDTOList;
    }

    private PokojnikSlikaMetaDTO mapPokojnikSlikaMetaToDTO(PokojnikSlikaMeta pokojnikSlikaMeta) {
        return new PokojnikSlikaMetaDTO(pokojnikSlikaMeta.getFid(), pokojnikSlikaMeta.getNaziv(), pokojnikSlikaMeta.getTip(), pokojnikSlikaMeta.getFk());
    }

}
