package it.geosolutions.mapstore.service;

import it.geosolutions.mapstore.dto.PokojnikSlikaMetaDTO;
import it.geosolutions.mapstore.model.PokojnikSlikaMeta;

import java.util.List;

public interface PokojnikSlikaMetaService {
    PokojnikSlikaMetaDTO getSlikaMetaByFid(Integer fid);
    List<PokojnikSlikaMetaDTO> getSlikaMetaByPokojnikFid(Integer fid);
}
