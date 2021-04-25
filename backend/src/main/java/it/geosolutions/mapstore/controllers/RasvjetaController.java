package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.rasvjeta.RasvjetaDTO;
import it.geosolutions.mapstore.model.rasvjeta.RasvjetaPutCommand;
import it.geosolutions.mapstore.service.rasvjeta.RasvjetaService;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class RasvjetaController {

    @Autowired
    private RasvjetaService rasvjetaService;

    /**
     * Gets all Rasvjeta data
     *
     * @param request
     * @param response
     * @param page
     * @throws IOException
     */

    @RequestMapping(value = "/rasvjeta", method = RequestMethod.GET)
    public void getRasvjeta(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "materijal", required = false) String materijal,
        @RequestParam(value = "stanje", required = false) String stanje,
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "geom", required = false) Boolean geom
    ) throws IOException {
        EntityListDTO rasvjetaListDTO;

        if(materijal != null || stanje != null) {

            Map<String, Object> params = new HashMap<String, Object>();

            if(materijal != null) {
                params.put("varchar:\"rasvjeta\".\"Materijal\" ", EncodingUtils.decodeISO88591(materijal).trim());
            }

            if(stanje != null) {
                params.put("varchar:\"rasvjeta\".\"Stanje\" ", EncodingUtils.decodeISO88591(stanje).trim());
            }

            rasvjetaListDTO = rasvjetaService.findSearch(params, "\"rasvjeta\"", page != null ? page : -1, geom != null);

        } else {

            if (page != null) {

                rasvjetaListDTO = rasvjetaService.findPaginated(page, geom != null);

            } else {

                rasvjetaListDTO = rasvjetaService.findAll(geom != null);

            }

        }

        HeaderUtils.responseWithJSON(response, JSONUtils.fromPOJOToJSON(rasvjetaListDTO));

    }

    @RequestMapping(value = "/rasvjeta/{idHist}", method = RequestMethod.GET)
    public void getRasvjetaByIdHist(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("idHist") Integer idHist,
        @RequestParam(value = "geom", required = false) Boolean geom
    ) throws IOException {

        Optional<RasvjetaDTO> oRasvjeta = rasvjetaService.findById(idHist, geom != null);

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
        @RequestParam(value = "geom", required = false) Boolean geom,
        @RequestBody String json
    ) throws IOException {

        String oJSON;

        RasvjetaPutCommand command = JSONUtils.fromJSONtoPOJO(json, RasvjetaPutCommand.class);

        Optional<RasvjetaDTO> rasvjeta = rasvjetaService.update(command, geom != null);

        if(rasvjeta.isPresent()) {

            oJSON = JSONUtils.fromPOJOToJSON(rasvjeta.get());

        } else {

            oJSON = "{}";

            response.setStatus(500);

        }

        HeaderUtils.responseWithJSON(response, oJSON);

    }

}
