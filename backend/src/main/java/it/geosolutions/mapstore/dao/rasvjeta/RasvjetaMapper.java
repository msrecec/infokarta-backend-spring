package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.model.Pokojnik;
import it.geosolutions.mapstore.model.Rasvjeta;
import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RasvjetaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        Rasvjeta rasvjeta = new Rasvjeta();

        rasvjeta.setGeom(((PGgeometry)rs.getObject(1)));
        rasvjeta.setFid(rs.getInt(2));
        rasvjeta.setMaterijal(rs.getString(3));
        rasvjeta.setStanje(rs.getString(4));
        rasvjeta.setSource(rs.getString(5));
        rasvjeta.setMjernoMjesto(rs.getString(6));
        rasvjeta.setVod(rs.getString(7));
        rasvjeta.setKategorija(rs.getString(8));
        rasvjeta.setVrstaRasvjetnogMjesta(rs.getString(9));
        rasvjeta.setRazdjelnik(rs.getString(10));
        rasvjeta.setTrosilo(rs.getString(11));
        rasvjeta.setVrstaSvjetiljke(rs.getString(12));
        rasvjeta.setBrojSvjetiljki(rs.getString(13));
        rasvjeta.setGrlo(rs.getString(14));
        rasvjeta.setVrstaStakla(rs.getString(15));
        rasvjeta.setPolozajKabela(rs.getString(16));
        rasvjeta.setGodinaIzgradnje(rs.getString(17));
        rasvjeta.setOznakaUgovora(rs.getString(18));
        rasvjeta.setIdHist(rs.getInt(19));
        rasvjeta.setTimeStart(rs.getObject(20, LocalDateTime.class));
        rasvjeta.setTimeEnd(rs.getObject(21, LocalDateTime.class));
        rasvjeta.setUserRole(rs.getString(22));

        return rasvjeta;
    }
}

