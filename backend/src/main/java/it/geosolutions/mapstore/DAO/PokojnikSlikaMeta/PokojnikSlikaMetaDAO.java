package it.geosolutions.mapstore.DAO.PokojnikSlikaMeta;

import it.geosolutions.mapstore.model.PokojnikSlikaMeta;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public interface PokojnikSlikaMetaDAO {
    public void setDataSource(DataSource ds);
    PokojnikSlikaMeta addSlika(PokojnikSlikaMeta pokojnikSlikaMeta);
    Optional<PokojnikSlikaMeta> getSlikaMetaByFid(Integer fid);
    List<PokojnikSlikaMeta> getSlikaMetaByPokojnikFid(Integer fid);

    PokojnikSlikaMeta addSlikaGrob(PokojnikSlikaMeta pokojnikSlikaMeta);
}
