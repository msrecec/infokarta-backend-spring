package it.geosolutions.mapstore;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


@Controller
public class PokojniciController {

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
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    public @ResponseBody String pokojnici() throws IOException, SQLException {
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

        while(rs.next()) {
            ogc_fid = rs.getInt(1);
            fid = rs.getInt(2);
            fk = rs.getInt(3);
            ime_i_prezime = rs.getString(4);
            prezime_djevojacko = rs.getString(5);
            ime_oca = rs.getString(6);
            nadimak = rs.getString(7);
            oib = rs.getString(8);
            spol = rs.getString(9);
            datum_rodjenja = rs.getString(10);
            field_10 = rs.getString(11);
            bracno_stanje = rs.getString(12);
            mjesto_stanovanja = rs.getString(13);
            adresa_stanovanja = rs.getString(14);
            ime_i_prezime_bracnog_druga = rs.getString(15);
            dob = rs.getString(16);
            uzrok_smrti = rs.getString(17);
            mjesto_smrti = rs.getString(18);
            datum_smrti = rs.getString(19);
            datum_kremiranja = rs.getString(20);
            datum_ukopa = rs.getString(21);
            oznaka_grobnice = rs.getString(22);
            groblje = rs.getString(23);
            naknadni_upisi_i_biljeske = rs.getString(24);
            godina_ukopa = rs.getString(25);
            usluga = rs.getString(26);
            racun = rs.getInt(27);
            datum_usluge = rs.getString(28);
        }

        closeConnectionToDatabase(veza);

        return "ovo su pokojnici" + ogc_fid + " " + fid + " " + fk + " " + ime_i_prezime + " " + prezime_djevojacko + " " + ime_oca + " " + nadimak + " " + oib + " " + spol + " "
            + datum_rodjenja + " " + field_10 + " " + nadimak + " " + bracno_stanje + " " + mjesto_stanovanja + " " + adresa_stanovanja + " " + ime_i_prezime_bracnog_druga + " " + dob + " " + uzrok_smrti + " " + mjesto_smrti + " " + datum_smrti + " " + datum_kremiranja
            + " " + datum_ukopa + " " + oznaka_grobnice + " " + groblje + " " + naknadni_upisi_i_biljeske + " " + godina_ukopa + " " + usluga + " " + racun + " " + datum_usluge;
    }

}
