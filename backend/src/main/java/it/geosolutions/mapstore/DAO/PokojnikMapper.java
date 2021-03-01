package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PokojnikMapper implements RowMapper {
    public Pokojnik mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pokojnik pokojnik = new Pokojnik();
        pokojnik.setFid(rs.getInt(1));
        pokojnik.setFk(rs.getInt(2));
        pokojnik.setIme_i_prezime(rs.getString(3));
        pokojnik.setPrezime_djevojacko(rs.getString(4));
        pokojnik.setIme_oca(rs.getString(5));
        pokojnik.setNadimak(rs.getString(6));
        pokojnik.setOib(rs.getString(7));
        pokojnik.setSpol(rs.getString(8));
        pokojnik.setDatum_rodjenja(rs.getString(9));
        pokojnik.setField_10(rs.getString(10));
        pokojnik.setBracno_stanje(rs.getString(11));
        pokojnik.setMjesto_stanovanja(rs.getString(12));
        pokojnik.setAdresa_stanovanja(rs.getString(13));
        pokojnik.setIme_i_prezime_bracnog_druga(rs.getString(14));
        pokojnik.setDob(rs.getString(15));
        pokojnik.setUzrok_smrti(rs.getString(16));
        pokojnik.setMjesto_smrti(rs.getString(17));
        pokojnik.setDatum_smrti(rs.getString(18));
        pokojnik.setDatum_kremiranja(rs.getString(19));
        pokojnik.setDatum_ukopa(rs.getString(20));
        pokojnik.setOznaka_grobnice(rs.getString(21));
        pokojnik.setGroblje(rs.getString(22));
        pokojnik.setNaknadni_upisi_i_biljeske(rs.getString(23));
        pokojnik.setGodina_ukopa(rs.getString(24));
        pokojnik.setUsluga(rs.getString(25));
        pokojnik.setRacun(rs.getInt(26));
        pokojnik.setDatum_usluge(rs.getString(27));

        return pokojnik;
    }
}
