package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.groblje.GrobljeDAO;
import it.geosolutions.mapstore.dao.groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.model.groblje.Groblje;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    GrobljeDAO grobljeDAO;
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/groblja", method = RequestMethod.GET)
    @ResponseBody
    public void getGroblja(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(request.toString());

        List<Groblje> groblja = grobljeDAO.listGroblja();

        String json = JSONUtils.fromListToJSON(groblja);

        HeaderUtils.responseWithJSON(response, json);
    }

}
