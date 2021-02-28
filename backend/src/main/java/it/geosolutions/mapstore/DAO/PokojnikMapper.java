package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PokojnikMapper implements RowMapper {
    public Pokojnik mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pokojnik pokojnik = new Pokojnik();
        pokojnik.setOgc_fid(rs.getInt(1));
        pokojnik.setFid(rs.getInt(2));
        pokojnik.setFk(rs.getInt(3));
        pokojnik.setIme_i_prezime(rs.getString(4));
        pokojnik.setPrezime_djevojacko(rs.getString(5));
        pokojnik.setIme_oca(rs.getString(6));
        pokojnik.setNadimak(rs.getString(7));
        pokojnik.setOib(rs.getString(8));
        pokojnik.setSpol(rs.getString(9));
        pokojnik.setDatum_rodjenja(rs.getString(10));
        pokojnik.setField_10(rs.getString(11));
        pokojnik.setBracno_stanje(rs.getString(12));
        pokojnik.setMjesto_stanovanja(rs.getString(13));
        pokojnik.setAdresa_stanovanja(rs.getString(14));
        pokojnik.setIme_i_prezime_bracnog_druga(rs.getString(15));
        pokojnik.setDob(rs.getString(16));
        pokojnik.setUzrok_smrti(rs.getString(17));
        pokojnik.setMjesto_smrti(rs.getString(18));
        pokojnik.setDatum_smrti(rs.getString(19));
        pokojnik.setDatum_kremiranja(rs.getString(20));
        pokojnik.setDatum_ukopa(rs.getString(21));
        pokojnik.setOznaka_grobnice(rs.getString(22));
        pokojnik.setGroblje(rs.getString(23));
        pokojnik.setNaknadni_upisi_i_biljeske(rs.getString(24));
        pokojnik.setGodina_ukopa(rs.getString(25));
        pokojnik.setUsluga(rs.getString(26));
        pokojnik.setRacun(rs.getInt(27));
        pokojnik.setDatum_usluge(rs.getString(28));

        return pokojnik;
    }
}
