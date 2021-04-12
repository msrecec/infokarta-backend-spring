package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.dao.pokojnik.PokojniciDAOImpl;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaListDTO;
import it.geosolutions.mapstore.model.Pokojnik;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import it.geosolutions.mapstore.service.rasvjeta.RasvjetaService;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class RasvjetaController {

    @Autowired
    private RasvjetaService rasvjetaService;

    @RequestMapping(value = "/rasvjeta", method = RequestMethod.GET)
    public void getRasvjeta(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "page", required = false) Integer page
    ) throws IOException {

        RasvjetaListDTO rasvjetaListDTO;

        Optional<Integer> oPage = Optional.ofNullable(page);

        if(oPage.isPresent()) {

            rasvjetaListDTO = rasvjetaService.findPaginated(oPage.get());

        } else {

            rasvjetaListDTO = rasvjetaService.findAll();

        }

        HeaderUtils.responseWithJSON(response, JSONUtils.fromPOJOToJSON(rasvjetaListDTO));

    }

    @RequestMapping(value = "/rasvjeta/{idHist}", method = RequestMethod.GET)
    public void getRasvjetaByIdHist(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("idHist") Integer idHist
    ) throws IOException {

        Optional<Rasvjeta> oRasvjeta = rasvjetaService.findById(idHist);

        String json = "{}";

        if(oRasvjeta.isPresent()) {

            json = JSONUtils.fromPOJOToJSON(oRasvjeta.get());

            response.setStatus(200);

        } else {

            response.setStatus(404);

        }

        HeaderUtils.responseWithJSON(response, json);

    }


    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/rasvjeta", method = RequestMethod.PUT)
    @ResponseBody
    public void updateRasvjeta(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody String json
    ) throws IOException {

        String oJSON;

        RasvjetaPutCommand command = JSONUtils.fromJSONtoPOJO(json, RasvjetaPutCommand.class);

        Optional<Rasvjeta> rasvjeta = rasvjetaService.update(command);

        if(rasvjeta.isPresent()) {

            oJSON = JSONUtils.fromPOJOToJSON(rasvjeta.get());

        } else {

            oJSON = "{}";

            response.setStatus(500);

        }

        HeaderUtils.responseWithJSON(response, oJSON);

    }

}
