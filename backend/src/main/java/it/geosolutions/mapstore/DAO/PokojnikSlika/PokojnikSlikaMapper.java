package it.geosolutions.mapstore.DAO.PokojnikSlika;

import it.geosolutions.mapstore.pojo.PokojnikSlika;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PokojnikSlikaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        PokojnikSlika pokojnikSlika = new PokojnikSlika();

        pokojnikSlika.setFid(rs.getInt(1));
        pokojnikSlika.setNaziv(rs.getString(2));
        pokojnikSlika.setSlika(rs.getBytes(3));
        pokojnikSlika.setTip(rs.getString(4));
        pokojnikSlika.setFk(rs.getInt(5));

        return pokojnikSlika;
    }
}
