package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobWithoutGeomDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.service.pokojnik.PokojnikService;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class PokojniciController {
    @Autowired
    PokojnikService pokojnikService;
    @Autowired
    PokojniciDAO pokojniciDAO;

    /**
     * Pokojnici dynamic search controller
     *
     * @param request
     * @param response
     * @param ime
     * @param prezime
     * @param pocGodinaUkopa
     * @param konGodinaUkopa
     * @param groblje
     * @param page
     * @throws IOException
     */

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
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "grobFid", required = false) Integer grobFid)
        throws IOException {
        String jsonArray;
        Optional<String> oIme = Optional.ofNullable(ime);
        Optional<String> oPrezime = Optional.ofNullable(prezime);
        Optional<String> oPocGodinaUkopa = Optional.ofNullable(pocGodinaUkopa);
        Optional<String> oKonGodinaUkopa = Optional.ofNullable(konGodinaUkopa);
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Integer> oPage = Optional.ofNullable(page);
        Optional<Integer> oGrobFid = Optional.ofNullable(grobFid);

        List<Pokojnik> pokojnici;

        if(oGrobFid.isPresent()) {
            pokojnici = pokojniciDAO.getPokojnikByGrobljeFid(oGrobFid.get());

            if(pokojnici.isEmpty()) {
                jsonArray = "[]";
                response.setStatus(404);
            } else {
                jsonArray = JSONUtils.fromListToJSON(pokojnici);
                response.setStatus(200);
            }

        } else {
            jsonArray = pokojniciDAO.searchPokojnici(oIme, oPrezime, oPocGodinaUkopa,
                oKonGodinaUkopa, oGroblje, oPage);

        }
        HeaderUtils.responseWithJSON(response, jsonArray);


    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getPokojnik(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "geom", required = false) Boolean geom,
        @RequestParam(value = "grob", required = false) Boolean grob,
        @PathVariable Integer id)
        throws IOException {
        Optional<Integer> oId = Optional.ofNullable(id);
        String json;

        if(grob == null) {
            Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oId);

            json = JSONUtils.fromPOJOToJSON(pokojnik);
        } else {
            if(geom == null) {
                PokojnikAndGrobWithoutGeomDTO pokojnikAndGrobWithoutGeomDTO = pokojnikService.getPokojnikAndGrobWithoutGeomByPokojnikFid(id);
                json = JSONUtils.fromPOJOToJSON(pokojnikAndGrobWithoutGeomDTO);
            } else {
                PokojnikAndGrobDTO pokojnikAndGrobDTO = pokojnikService.getPokojnikAndGrobByPokojnikFid(id);
                json = JSONUtils.fromPOJOToJSON(pokojnikAndGrobDTO);
            }
        }


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
        Integer numberOfAffectedRows;

        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<String> oRbr = Optional.ofNullable(rbr);

        Pokojnik pokojnik = JSONUtils.fromJSONtoPOJO(json, Pokojnik.class);

        if(oGroblje.isPresent() && oRbr.isPresent()) {

            numberOfAffectedRows = pokojniciDAO.addPokojnikByGrobljeAndRbr(
                pokojnik,
                EncodingUtils.decodeISO88591(oGroblje.get()),
                EncodingUtils.decodeISO88591(oRbr.get())
            );

        } else {

            numberOfAffectedRows = pokojniciDAO.addPokojnik(pokojnik);

        }

        if(numberOfAffectedRows <= 0) {
            response.setStatus(500);
        } else {
            response.setStatus(200);
        }

        outJson = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";

        HeaderUtils.responseWithJSON(response, outJson);
    }
}
