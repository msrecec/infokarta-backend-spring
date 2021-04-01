package it.geosolutions.mapstore.dao.slika;

import it.geosolutions.mapstore.model.SlikaMeta;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SlikaMetaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        SlikaMeta slikaMeta = new SlikaMeta();

        slikaMeta.setFid(rs.getInt(1));
        slikaMeta.setNaziv(rs.getString(2));
        slikaMeta.setLokacija(rs.getString(3));
        slikaMeta.setTip(rs.getString(4));
        slikaMeta.setFk(rs.getInt(5));

        return slikaMeta;
    }
}
