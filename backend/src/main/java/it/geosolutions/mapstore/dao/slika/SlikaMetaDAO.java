package it.geosolutions.mapstore.dao.slika;

import it.geosolutions.mapstore.model.slikaMeta.SlikaMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface SlikaMetaDAO {
    Optional<SlikaMeta> getSlikaMetaByFid(Integer fk, String entityTable);
    List<SlikaMeta> getSlikaMetaByEntityFid(Integer fid, String entity);

    SlikaMeta addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity);
}
