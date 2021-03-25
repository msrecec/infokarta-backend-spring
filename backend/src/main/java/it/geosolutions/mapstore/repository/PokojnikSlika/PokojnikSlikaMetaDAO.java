package it.geosolutions.mapstore.repository.PokojnikSlika;

import it.geosolutions.mapstore.model.PokojnikSlikaMeta;

import javax.sql.DataSource;

public interface PokojnikSlikaMetaDAO {
    public void setDataSource(DataSource ds);
    PokojnikSlikaMeta addSlika(PokojnikSlikaMeta pokojnikSlikaMeta);
    PokojnikSlikaMeta getSlikaMetaByFid(Integer fid);
}
