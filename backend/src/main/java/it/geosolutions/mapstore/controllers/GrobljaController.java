package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.repository.Groblje.GrobljeDAO;
import it.geosolutions.mapstore.repository.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.model.Groblje;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class GrobljaController {
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/groblja", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGroblja() throws UnsupportedEncodingException {

        GrobljeDAO grobljeDAO = new GrobljeDAOImpl();

        List<Groblje> groblja = grobljeDAO.listGroblja();

        String json = JSONUtils.fromListToJSON(groblja);

        return json.getBytes("UTF-8");
    }

}
