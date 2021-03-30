package it.geosolutions.mapstore.DAO.SlikaMeta;

import it.geosolutions.mapstore.model.SlikaMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface SlikaMetaDAO {
    public void setDataSource(DataSource ds);
    SlikaMeta addSlika(SlikaMeta slikaMeta);
    Optional<SlikaMeta> getSlikaMetaByFid(Integer fid);
    List<SlikaMeta> getSlikaMetaByPokojnikFid(Integer fid);

    SlikaMeta addSlikaGrob(SlikaMeta slikaMeta);
}
