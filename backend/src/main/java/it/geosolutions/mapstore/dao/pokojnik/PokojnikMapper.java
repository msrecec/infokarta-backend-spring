package it.geosolutions.mapstore.dao.pokojnik;

import it.geosolutions.mapstore.model.Pokojnik;
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
        pokojnik.setBracno_stanje(rs.getString(10));
        pokojnik.setMjesto_stanovanja(rs.getString(11));
        pokojnik.setAdresa_stanovanja(rs.getString(12));
        pokojnik.setIme_i_prezime_bracnog_druga(rs.getString(13));
        pokojnik.setDob(rs.getString(14));
        pokojnik.setUzrok_smrti(rs.getString(15));
        pokojnik.setMjesto_smrti(rs.getString(16));
        pokojnik.setDatum_smrti(rs.getString(17));
        pokojnik.setDatum_kremiranja(rs.getString(18));
        pokojnik.setDatum_ukopa(rs.getString(19));
        pokojnik.setOznaka_grobnice(rs.getString(20));
        pokojnik.setGroblje(rs.getString(21));
        pokojnik.setNaknadni_upisi_i_biljeske(rs.getString(22));
        pokojnik.setGodina_ukopa(rs.getString(23));
        pokojnik.setUsluga(rs.getString(24));
        pokojnik.setRacun(rs.getInt(25));
        pokojnik.setDatum_usluge(rs.getString(26));
        pokojnik.setIme(rs.getString(27));
        pokojnik.setPrezime(rs.getString(28));

        return pokojnik;
    }
}
