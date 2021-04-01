package it.geosolutions.mapstore.dao.slikaMeta;

import it.geosolutions.mapstore.model.SlikaMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface SlikaMetaDAO {
    public void setDataSource(DataSource ds);
    Optional<SlikaMeta> getSlikaMetaByFid(Integer fk, String entityTable);
    List<SlikaMeta> getSlikaMetaByEntityFid(Integer fid, String entityTable, String entity);

    SlikaMeta addSlikaGrob(SlikaMeta slikaMeta);

    SlikaMeta addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity);
    SlikaMeta addSlikaOriginalMetaByEntity(SlikaMeta slikaMeta, String entity);
    SlikaMeta addSlikaThumbnailMetaByEntity(SlikaMeta slikaMeta, String entity);
}
