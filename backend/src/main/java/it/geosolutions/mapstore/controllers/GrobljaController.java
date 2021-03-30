package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAO;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.model.Groblje;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.ResponseHeaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class GrobljaController {
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/groblja", method = RequestMethod.GET)
    @ResponseBody
    public void getGroblja(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GrobljeDAO grobljeDAO = new GrobljeDAOImpl();

        List<Groblje> groblja = grobljeDAO.listGroblja();

        String json = JSONUtils.fromListToJSON(groblja);

        ResponseHeaderUtils.headerResponseWithJSON(response, json);
    }

}
