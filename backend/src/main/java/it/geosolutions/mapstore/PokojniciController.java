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
        ResultSet rs = stmt.executeQuery("select * from geostore.grobovi limit 1");

        Integer ogc_fid = null;
        String wkb_geometry = null;
        Integer fid = null;
        String layer = null;
        String source = null;

        while(rs.next()) {
            ogc_fid = rs.getInt("ogc_fid");
            wkb_geometry = rs.getString("wkb_geometry");
            fid = rs.getInt("fid");
            layer = rs.getString("layer");
            source = rs.getString("source");
        }

        closeConnectionToDatabase(veza);

        System.out.println("ovo su pokojnici " + ogc_fid + " " + wkb_geometry + " " + fid + " " + layer + " " + source);
        return "ovo su pokojnici " + ogc_fid + " " + wkb_geometry + " " + fid + " " + layer + " " + source;
    }

}
