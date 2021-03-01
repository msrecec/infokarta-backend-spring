package it.geosolutions.mapstore;

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

import java.util.List;


@Controller
public class PokojniciController {

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    public @ResponseBody String getPokojnici() {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        List<Pokojnik> pokojnici = pokojniciDAO.listPokojnici();

        String jsonArray = JSONUtils.fromListToJSON(pokojnici);

        return jsonArray;

    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    public @ResponseBody String getPokojnik(@PathVariable Integer id) {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = pokojniciDAO.getPokojnik(id);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        return json;
    }
}
