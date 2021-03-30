package it.geosolutions.mapstore.service;

import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.SlikaMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface SlikaMetaService {
    public SlikaMetaDTO getSlikaMetaByFid(Integer fid, String entity);
    List<SlikaMetaDTO> getSlikaMetaByPokojnikFid(Integer fid);
    ByteArrayOutputStream createThumbnail(MultipartFile originalFile, Integer width);
    SlikaMetaDTO addSlikaToEntity(SlikaMeta slikaMeta, String entity);
}
