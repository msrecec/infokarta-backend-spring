package it.geosolutions.mapstore.service;

import it.geosolutions.mapstore.DAO.SlikaMeta.SlikaMetaDAO;
import it.geosolutions.mapstore.DAO.SlikaMeta.SlikaMetaDAOImpl;
import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.SlikaMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlikaMetaServiceImpl implements SlikaMetaService {

    private SlikaMetaDAO slikaMetaDAO;

    public SlikaMetaServiceImpl() {
        this.slikaMetaDAO = new SlikaMetaDAOImpl();
    }

    @Override
    public SlikaMetaDTO getSlikaMetaByFid(Integer fid) {

        Optional<SlikaMeta> oPokojnikSlikaMeta = slikaMetaDAO.getSlikaMetaByFid(fid);

        if(oPokojnikSlikaMeta.isPresent()) {
            return mapPokojnikSlikaMetaToDTO(oPokojnikSlikaMeta.get());
        } else {
            return null;
        }
    }

    @Override
    public List<SlikaMetaDTO> getSlikaMetaByPokojnikFid(Integer fid) {
        slikaMetaDAO.getSlikaMetaByPokojnikFid(fid);
        List<SlikaMetaDTO> slikaMetaDTOList = new ArrayList<>();
        List<SlikaMeta> slikaMetaList = slikaMetaDAO.getSlikaMetaByPokojnikFid(fid);

        for(SlikaMeta p : slikaMetaList) {
            slikaMetaDTOList.add(mapPokojnikSlikaMetaToDTO(p));
        }

        return slikaMetaDTOList;
    }

    private SlikaMetaDTO mapPokojnikSlikaMetaToDTO(SlikaMeta slikaMeta) {
        return new SlikaMetaDTO(slikaMeta.getFid(), slikaMeta.getNaziv(), slikaMeta.getTip(), slikaMeta.getFk());
    }

}
