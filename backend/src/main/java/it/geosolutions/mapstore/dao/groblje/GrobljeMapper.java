package it.geosolutions.mapstore.dao.groblje;

import it.geosolutions.mapstore.model.Groblje;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrobljeMapper  implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Groblje groblje = new Groblje();

        groblje.setFid(rs.getInt(1));
        groblje.setNaziv(rs.getString(2));

        return groblje;
    }
}
