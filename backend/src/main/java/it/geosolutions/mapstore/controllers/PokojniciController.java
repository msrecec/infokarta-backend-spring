package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikAndGrobDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.service.pokojnik.PokojnikService;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


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

    /**
     * TODO - Finish this implementation and remove ugly previous impl
     *
     * @param request
     * @param response
     * @param ime
     * @param prezime
     * @param godinaUkopa
     * @param groblje
     * @param page
     * @param grobFid
     * @throws IOException
     */

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici2", method = RequestMethod.GET)
    @ResponseBody
    public void getPokojnici2(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "ime", required = false) String ime,
        @RequestParam(value = "prezime", required = false) String prezime,
        @RequestParam(value = "godina-ukopa", required = false) String godinaUkopa,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "grobFid", required = false) Integer grobFid)
        throws IOException {
        String jsonArray;
        EntityListDTO pokojniciDTOList;
        List<Pokojnik> pokojnici;

        if(grobFid != null) {
            pokojnici = pokojniciDAO.getPokojnikByGrobljeFid(grobFid);

            if(pokojnici.isEmpty()) {
                jsonArray = "[]";
                response.setStatus(404);
            } else {
                jsonArray = JSONUtils.fromListToJSON(pokojnici);
                response.setStatus(200);
            }

        } else {
            Map<String, Object> params = new HashMap<String, Object>();

            SearchEntity entity = new SearchEntity("\"pokojnici\"", "\"fid\"", "\"fk\"");

            if(ime != null) {
                params.put("varchar:\"pokojnici\".\"IME\":ilike", EncodingUtils.decodeISO88591(ime).trim());
            }

            if(prezime != null) {
                params.put("varchar:\"pokojnici\".\"PREZIME\":ilike", EncodingUtils.decodeISO88591(prezime).trim());
            }

            if(godinaUkopa != null) {
                params.put("varchar:\"pokojnici\".\"Godina ukopa\":ilike", EncodingUtils.decodeISO88591(godinaUkopa).trim());
            }

            if(groblje != null) {
                params.put("varchar:\"groblja\".\"naziv\":ilike", EncodingUtils.decodeISO88591(groblje).trim());
            }

            List<SearchEntity> orderedEntities = new ArrayList<>();

            orderedEntities.add(new SearchEntity("\"grobovi\"", "\"fid\"", "\"fk\""));
            orderedEntities.add(new SearchEntity("\"groblja\"", "\"fid\"", "\"fk\""));

            EntityListDTO entities = pokojnikService.fullSearch(params, entity, page != null ? page : -1, orderedEntities);

            if(entities.getEntityList().isEmpty()) {
                response.setStatus(404);
            } else {
                response.setStatus(200);
            }

            jsonArray = JSONUtils.fromPOJOToJSON(entities);

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
                PokojnikAndGrobDTO pokojnikAndGrobWithoutGeomDTO = pokojnikService.getPokojnikAndGrobWithoutGeomByPokojnikFid(id);
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
