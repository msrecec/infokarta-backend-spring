package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAOImpl;
import it.geosolutions.mapstore.model.Rasvjeta;
import it.geosolutions.mapstore.service.rasvjeta.RasvjetaService;
import it.geosolutions.mapstore.service.rasvjeta.RasvjetaServiceImpl;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class RasvjetaController {

    private RasvjetaService rasvjetaService;

    @RequestMapping(value = "/rasvjeta", method = RequestMethod.GET)
    public void getRasvjeta(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {

        RasvjetaDAO rasvjetaDAO = new RasvjetaDAOImpl();

        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();

        HeaderUtils.responseWithJSON(response, JSONUtils.fromListToJSON(rasvjeta));

    }

    @RequestMapping(value = "/rasvjeta/{idHist}", method = RequestMethod.GET)
    public void getRasvjetaByIdHist(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("idHist") Integer idHist
    ) throws IOException {


        rasvjetaService = new RasvjetaServiceImpl();

        Optional<Rasvjeta> oRasvjeta = rasvjetaService.findByIdHist(idHist);

        String json = "{}";

        if(oRasvjeta.isPresent()) {

            json = JSONUtils.fromPOJOToJSON(oRasvjeta.get());

            response.setStatus(200);

        } else {

            response.setStatus(404);

        }

        HeaderUtils.responseWithJSON(response, json);

    }
}
