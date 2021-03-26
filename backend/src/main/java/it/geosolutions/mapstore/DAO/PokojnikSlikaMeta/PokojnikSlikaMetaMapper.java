package it.geosolutions.mapstore.DAO.PokojnikSlikaMeta;

import it.geosolutions.mapstore.model.PokojnikSlikaMeta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PokojnikSlikaMetaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        PokojnikSlikaMeta pokojnikSlikaMeta = new PokojnikSlikaMeta();

        pokojnikSlikaMeta.setFid(rs.getInt(1));
        pokojnikSlikaMeta.setNaziv(rs.getString(2));
        pokojnikSlikaMeta.setLokacija(rs.getString(3));
        pokojnikSlikaMeta.setTip(rs.getString(4));
        pokojnikSlikaMeta.setFk(rs.getInt(5));

        return pokojnikSlikaMeta;
    }
}
