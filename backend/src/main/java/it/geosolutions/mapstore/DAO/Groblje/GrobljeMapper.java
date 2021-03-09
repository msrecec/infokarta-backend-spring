package it.geosolutions.mapstore.DAO.Groblje;

import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrobljeMapper  implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Groblje groblje = new Groblje();
        groblje.setNaziv(rs.getString(1));
        groblje.setBrojGrobova(rs.getInt(2));

        return groblje;
    }
}
