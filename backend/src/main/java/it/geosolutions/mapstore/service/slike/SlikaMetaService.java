package it.geosolutions.mapstore.service.slike;

import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.SlikaMeta;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface SlikaMetaService {
    public SlikaMetaDTO getSlikaMetaByFid(Integer fid, String entity);
    List<SlikaMetaDTO> getSlikaMetaByEntityFid(Integer fid, String entity);
    ByteArrayOutputStream createThumbnail(MultipartFile originalFile, Integer width) throws IOException;
    SlikaMetaDTO addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity);
}
