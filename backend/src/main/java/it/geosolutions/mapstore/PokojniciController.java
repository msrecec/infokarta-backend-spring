package it.geosolutions.mapstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.geosolutions.mapstore.DAO.PokojniciDAO;
import it.geosolutions.mapstore.DAO.PokojniciDAOImpl;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;


@Controller
public class PokojniciController {

    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    public @ResponseBody String getPokojnici() {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        List<Pokojnik> pokojnici = pokojniciDAO.listPokojnici();

        String stringData = JSONUtils.fromListToJSON(pokojnici);

        return stringData;

    }

    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    public @ResponseBody String getPokojnik(@PathVariable Integer id) {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = pokojniciDAO.getPokojnik(id);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        return json;
    }

    public static Connection connectToDatabase() throws SQLException, IOException {
        Properties svojstva = new Properties();

        String urlBazePodataka = "jdbc:postgresql://localhost:5432/geostore";
        String korisnickoIme = "geostore";
        String lozinka = "geostore";

        Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

        return veza;
    }

    public static void closeConnectionToDatabase(Connection veza) throws SQLException {
        veza.close();
    }
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici2", method = RequestMethod.GET)
    public @ResponseBody String pokojnici2() throws IOException, SQLException {
        Connection veza = connectToDatabase();
        Statement stmt = veza.createStatement();
        ResultSet rs = stmt.executeQuery("select * from geostore.pokojnici limit 1");

        Integer ogc_fid = null;
        Integer fid = null;
        Integer fk = null;
        String ime_i_prezime = null;
        String prezime_djevojacko = null;
        String ime_oca = null;
        String nadimak = null;
        String oib = null;
        String spol = null;
        String datum_rodjenja = null;
        String field_10 = null;
        String bracno_stanje = null;
        String mjesto_stanovanja = null;
        String adresa_stanovanja = null;
        String ime_i_prezime_bracnog_druga = null;
        String dob = null;
        String uzrok_smrti = null;
        String mjesto_smrti = null;
        String datum_smrti = null;
        String datum_kremiranja = null;
        String datum_ukopa = null;
        String oznaka_grobnice = null;
        String groblje = null;
        String naknadni_upisi_i_biljeske = null;
        String godina_ukopa = null;
        String usluga = null;
        Integer racun = null;
        String datum_usluge = null;

        Pokojnik pokojnik = new Pokojnik();

        while(rs.next()) {
//            ogc_fid = rs.getInt(1);
//            fid = rs.getInt(2);
//            fk = rs.getInt(3);
//            ime_i_prezime = rs.getString(4);
//            prezime_djevojacko = rs.getString(5);
//            ime_oca = rs.getString(6);
//            nadimak = rs.getString(7);
//            oib = rs.getString(8);
//            spol = rs.getString(9);
//            datum_rodjenja = rs.getString(10);
//            field_10 = rs.getString(11);
//            bracno_stanje = rs.getString(12);
//            mjesto_stanovanja = rs.getString(13);
//            adresa_stanovanja = rs.getString(14);
//            ime_i_prezime_bracnog_druga = rs.getString(15);
//            dob = rs.getString(16);
//            uzrok_smrti = rs.getString(17);
//            mjesto_smrti = rs.getString(18);
//            datum_smrti = rs.getString(19);
//            datum_kremiranja = rs.getString(20);
//            datum_ukopa = rs.getString(21);
//            oznaka_grobnice = rs.getString(22);
//            groblje = rs.getString(23);
//            naknadni_upisi_i_biljeske = rs.getString(24);
//            godina_ukopa = rs.getString(25);
//            usluga = rs.getString(26);
//            racun = rs.getInt(27);
//            datum_usluge = rs.getString(28);
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
        }

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        closeConnectionToDatabase(veza);

//        return "ovo su pokojnici" + ogc_fid + " " + fid + " " + fk + " " + ime_i_prezime + " " + prezime_djevojacko + " " + ime_oca + " " + nadimak + " " + oib + " " + spol + " "
//            + datum_rodjenja + " " + field_10 + " " + nadimak + " " + bracno_stanje + " " + mjesto_stanovanja + " " + adresa_stanovanja + " " + ime_i_prezime_bracnog_druga + " " + dob + " " + uzrok_smrti + " " + mjesto_smrti + " " + datum_smrti + " " + datum_kremiranja
//            + " " + datum_ukopa + " " + oznaka_grobnice + " " + groblje + " " + naknadni_upisi_i_biljeske + " " + godina_ukopa + " " + usluga + " " + racun + " " + datum_usluge;
        return json;
    }

}
