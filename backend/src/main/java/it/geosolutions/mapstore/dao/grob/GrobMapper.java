package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.model.Grob;
import org.postgis.PGgeometry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrobMapper implements RowMapper {
    @Override
    public Grob mapRow(ResultSet rs, int i) throws SQLException {
        Grob grob = new Grob();

        grob.setGeom((PGgeometry)rs.getObject(1));
        grob.setFid(rs.getInt(2));
        grob.setSource(rs.getString(3));
        grob.setSource1(rs.getString(4));
        grob.setSource2(rs.getString(5));
        grob.setSource3(rs.getString(6));
        grob.setSource4(rs.getString(7));
        grob.setSource5(rs.getString(8));
        grob.setSource6(rs.getString(9));
        grob.setSource7(rs.getString(10));
        grob.setRedniBroj(rs.getString(11));
        grob.setGrobnica(rs.getString(12));
        grob.setBrojLezaja(rs.getString(13));
        grob.setGroblje(rs.getString(14));

        return grob;
    }
}
