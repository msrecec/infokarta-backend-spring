package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAO;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.pojo.Groblje;
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

        List<Groblje> groblja = grobljeDAO.listGroblje();

        String json = JSONUtils.fromListToJSON(groblja);

        return json.getBytes("UTF-8");
    }

}
