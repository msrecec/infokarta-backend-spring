package it.geosolutions.mapstore.dao.slikaMeta.mappers;

import it.geosolutions.mapstore.model.slike.Slika;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SlikaMapper implements RowMapper {

    void setSlika(Slika slika, ResultSet rs) throws SQLException {

        slika.setFid(rs.getInt(1));
        slika.setNaziv(rs.getString(2));
        slika.setTip(rs.getString(3));
        slika.setMediaFid(rs.getInt(4));
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        Slika slika = new Slika();

        setSlika(slika, rs);

        return slika;
    }
}
