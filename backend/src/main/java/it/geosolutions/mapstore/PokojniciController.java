package it.geosolutions.mapstore;

import it.geosolutions.mapstore.DAO.PokojniciDAO;
import it.geosolutions.mapstore.DAO.PokojniciDAOImpl;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class PokojniciController {

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnici(
        @RequestParam(value = "ime", required = false) String ime,
        @RequestParam(value = "prezime", required = false) String prezime,
        @RequestParam(value = "godinaukopa", required = false) String godinaUkopa,
        @RequestParam(value = "page", required = false) Integer page)
        throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        List<Pokojnik> pokojnici = new ArrayList<>();
        String jsonArray;
        Optional<String> oIme = Optional.ofNullable(ime);
        Optional<String> oPrezime = Optional.ofNullable(prezime);
        Optional<String> oGodinaUkopa = Optional.ofNullable(godinaUkopa);
        Optional<Integer> oPage = Optional.ofNullable(page);

        if(!oIme.isPresent() && !oPrezime.isPresent() && !oGodinaUkopa.isPresent() && !oPage.isPresent()) {
            pokojnici.addAll(pokojniciDAO.listPokojnici());
        }
        else {
            pokojnici.addAll(pokojniciDAO.getPokojnikByImeOrPrezimeOrPage(oIme, oPrezime, oGodinaUkopa, oPage));
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
}
