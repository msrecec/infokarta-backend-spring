package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAO;
import it.geosolutions.mapstore.dao.rasvjeta.RasvjetaDAOImpl;
import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.Rasvjeta;
import it.geosolutions.mapstore.service.SlikaMetaService;
import it.geosolutions.mapstore.service.SlikaMetaServiceImpl;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.EntityUtil;
import it.geosolutions.mapstore.utils.HeaderUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class RasvjetaController {

    @RequestMapping(value = "/rasvjeta", method = RequestMethod.GET)
    public void getRasvjeta(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {

        RasvjetaDAO rasvjetaDAO = new RasvjetaDAOImpl();

        List<Rasvjeta> rasvjeta = rasvjetaDAO.findAll();

        System.out.println(rasvjeta.get(0).getGeom().getType());

        HeaderUtils.responseWithJSON(response, JSONUtils.fromListToJSON(rasvjeta));

    }

    @RequestMapping(value = "/rasvjeta/{idHist}", method = RequestMethod.GET)
    public void getRasvjetaByIdHist(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("idHist") Integer idHist
    ) throws IOException {


        RasvjetaDAO rasvjetaDAO = new RasvjetaDAOImpl();

        Rasvjeta rasvjeta = rasvjetaDAO.findByIdHist(idHist);

        HeaderUtils.responseWithJSON(response, JSONUtils.fromPOJOToJSON(rasvjeta));

    }

}
