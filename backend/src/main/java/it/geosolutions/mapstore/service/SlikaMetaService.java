package it.geosolutions.mapstore.service;

import it.geosolutions.mapstore.dto.SlikaMetaDTO;

import java.util.List;

public interface SlikaMetaService {
    SlikaMetaDTO getSlikaMetaByFid(Integer fid);
    List<SlikaMetaDTO> getSlikaMetaByPokojnikFid(Integer fid);
}
