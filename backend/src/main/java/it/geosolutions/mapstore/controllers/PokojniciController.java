package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.DAO.Pokojnik.PokojniciDAOImpl;
import it.geosolutions.mapstore.model.Pokojnik;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;


@Controller
public class PokojniciController {

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    @ResponseBody
    public void getPokojnici(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "ime", required = false) String ime,
        @RequestParam(value = "prezime", required = false) String prezime,
        @RequestParam(value = "pocgodinaukopa", required = false) String pocGodinaUkopa,
        @RequestParam(value = "kongodinaukopa", required = false) String konGodinaUkopa,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "page", required = false) Integer page)
        throws IOException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        String jsonArray;
        Optional<String> oIme = Optional.ofNullable(ime);
        Optional<String> oPrezime = Optional.ofNullable(prezime);
        Optional<String> oPocGodinaUkopa = Optional.ofNullable(pocGodinaUkopa);
        Optional<String> oKonGodinaUkopa = Optional.ofNullable(konGodinaUkopa);
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Integer> oPage = Optional.ofNullable(page);

        jsonArray = pokojniciDAO.searchPokojnici(oIme, oPrezime, oPocGodinaUkopa,
            oKonGodinaUkopa, oGroblje, oPage);

        HeaderUtils.responseWithJSON(response, jsonArray);

    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getPokojnik(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable Integer id)
        throws IOException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        Optional<Integer> oId = Optional.ofNullable(id);

        Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oId);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        HeaderUtils.responseWithJSON(response, json);
    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/columns", method = RequestMethod.GET)
    @ResponseBody
    public void getColumns(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "variables", required = false) Boolean variables
    ) throws IOException {

        String json;

        Optional<Boolean> oVariables = Optional.ofNullable(variables);

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        if(oVariables.isPresent()) {
            Pokojnik pokojnik = pokojniciDAO.getFirstPokojnik();

            json = JSONUtils.fromPOJOToJSON(pokojnik);
        } else {
            List<String> columns = pokojniciDAO.listColumns();

            json = JSONUtils.fromListToJSON(columns);
        }

        HeaderUtils.responseWithJSON(response, json);
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/count", method = RequestMethod.GET)
    @ResponseBody
    public void getCount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Integer count = pokojniciDAO.getPokojnikCount();

        String json = "{" + "\"count\":"+ "\"" + count + "\"" +"}";

        HeaderUtils.responseWithJSON(response, json);
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.PUT)
    @ResponseBody
    public void updatePokojnici(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody String json
    ) throws IOException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = JSONUtils.fromJSONtoPOJO(json, Pokojnik.class);

        String outJson = pokojniciDAO.updatePokojnik(pokojnik);

        HeaderUtils.responseWithJSON(response, outJson);
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.POST)
    @ResponseBody
    public void addPokojnik(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody String json,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "rbr", required = false) String rbr
    ) throws IOException {

        String outJson;

        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<String> oRbr = Optional.ofNullable(rbr);

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = JSONUtils.fromJSONtoPOJO(json, Pokojnik.class);

        if(oGroblje.isPresent() && oRbr.isPresent()) {

            outJson = pokojniciDAO.addPokojnikByGrobljeAndRbr(
                pokojnik,
                EncodingUtils.decodeISO88591(oGroblje.get()),
                EncodingUtils.decodeISO88591(oRbr.get())
            );

        } else {

            outJson = pokojniciDAO.addPokojnik(pokojnik);

        }

        HeaderUtils.responseWithJSON(response, outJson);
    }
}
