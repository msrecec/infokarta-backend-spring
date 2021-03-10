package it.geosolutions.mapstore;

import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAO;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.DAO.Pokojnici.PokojniciDAO;
import it.geosolutions.mapstore.DAO.Pokojnici.PokojniciDAOImpl;
import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class InfokartaController {

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnici(
        @RequestParam(value = "ime", required = false) String ime,
        @RequestParam(value = "prezime", required = false) String prezime,
        @RequestParam(value = "pocgodinaukopa", required = false) String pocGodinaUkopa,
        @RequestParam(value = "kongodinaukopa", required = false) String konGodinaUkopa,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "page", required = false) Integer page)
        throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        List<Pokojnik> pokojnici = new ArrayList<>();
        String jsonArray;
        Optional<String> oIme = Optional.ofNullable(ime);
        Optional<String> oPrezime = Optional.ofNullable(prezime);
        Optional<String> oPocGodinaUkopa = Optional.ofNullable(pocGodinaUkopa);
        Optional<String> oKonGodinaUkopa = Optional.ofNullable(konGodinaUkopa);
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Integer> oPage = Optional.ofNullable(page);

        if(!oIme.isPresent() && !oPrezime.isPresent() && !oPocGodinaUkopa.isPresent() && !oKonGodinaUkopa.isPresent()
            && !oPage.isPresent() && !oGroblje.isPresent()) {
            pokojnici.addAll(pokojniciDAO.listPokojnici());
        }
        else {
            pokojnici.addAll(pokojniciDAO.searchPokojnici(oIme, oPrezime, oPocGodinaUkopa,
                oKonGodinaUkopa, oGroblje, oPage));
        }

        jsonArray = JSONUtils.fromListToJSON(pokojnici);

        return jsonArray.getBytes("UTF-8");

    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnik(@PathVariable Integer id) throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        Optional<Integer> oId = Optional.ofNullable(id);

        Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oId);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        return json.getBytes("UTF-8");
    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/columns", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getColumns() throws UnsupportedEncodingException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        List<String> columns = pokojniciDAO.listColumns();

        String json = JSONUtils.fromListToJSON(columns);

        return json.getBytes("UTF-8");
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/count", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getCount() throws UnsupportedEncodingException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Integer count = pokojniciDAO.getPokojnikCount();

        String json = "{" + "\"count\":"+ "\"" + count + "\"" +"}";

        return json.getBytes("UTF-8");
    }
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/groblja", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGroblja() throws UnsupportedEncodingException {

        GrobljeDAO grobljeDAO = new GrobljeDAOImpl();

        List<Groblje > groblja = grobljeDAO.listGroblje();

        String json = JSONUtils.fromListToJSON(groblja);

        return json.getBytes("UTF-8");
    }



}
