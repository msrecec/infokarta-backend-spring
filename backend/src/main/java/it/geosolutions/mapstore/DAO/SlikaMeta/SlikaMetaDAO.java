package it.geosolutions.mapstore.DAO.SlikaMeta;

import it.geosolutions.mapstore.model.SlikaMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface SlikaMetaDAO {
    public void setDataSource(DataSource ds);
    Optional<SlikaMeta> getSlikaMetaByFid(Integer fk, String entityTable);
    List<SlikaMeta> getSlikaMetaByEntityFid(Integer fid, String entityTable, String entity);

    SlikaMeta addSlikaGrob(SlikaMeta slikaMeta);

    SlikaMeta addSlikaToEntity(SlikaMeta slikaMeta, String entity);
}
