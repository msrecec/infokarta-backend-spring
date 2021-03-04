package it.geosolutions.mapstore;

import it.geosolutions.mapstore.DAO.PokojniciDAO;
import it.geosolutions.mapstore.DAO.PokojniciDAOImpl;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;


@Controller
public class PokojniciController {

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnici() throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        List<Pokojnik> pokojnici = pokojniciDAO.listPokojnici();

        String jsonArray = JSONUtils.fromListToJSON(pokojnici);

        return jsonArray.getBytes("UTF-8");

    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnik(@PathVariable Integer id) throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = pokojniciDAO.getPokojnik(id);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        return json.getBytes("UTF-8");
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/columns", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getColumns() throws UnsupportedEncodingException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        List<String> columns = pokojniciDAO.listColumns();

        String json = JSONUtils.fromListToJSON(columns);

        return json.getBytes("UTF-8");
    }
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/page/{pageNum}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPage(@PathVariable Integer pageNum) throws UnsupportedEncodingException {
        String json = "";

        if(pageNum > 0) {

            PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

            List<Pokojnik> columns = pokojniciDAO.listPage(pageNum);

            json = JSONUtils.fromListToJSON(columns);
        }

        return json.getBytes("UTF-8");
    }
}
