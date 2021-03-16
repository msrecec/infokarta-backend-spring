package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Grob.GrobDAOImpl;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.DAO.Grob.GrobDAO;
import it.geosolutions.mapstore.pojo.Grob;
import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
public class GroboviController {
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGrobovi() throws UnsupportedEncodingException {

        GrobDAO grobDAO = new GrobDAOImpl();

        List<Grob> grobovi = grobDAO.listGrobovi();

        String json = JSONUtils.fromListToJSON(grobovi);

        return json.getBytes("UTF-8");
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi/rbr", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGroboviRBR(@RequestParam(value = "groblje", required = false) String groblje) throws UnsupportedEncodingException {

        GrobDAO grobDAO = new GrobDAOImpl();
        List<Grob> grobovi;
        Optional<String> oGroblje = Optional.ofNullable(groblje);

        if(oGroblje.isPresent()){
            grobovi = grobDAO.getRBRGrobovaByGroblje(oGroblje.get());
        } else {
            grobovi = grobDAO.getRBRGrobova();

        }

        String json = JSONUtils.fromListToJSON(grobovi);

        return json.getBytes("UTF-8");
    }




    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi/{rbr}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGrobByRBR(@PathVariable String rbr) throws UnsupportedEncodingException {

        GrobDAO grobDAO = new GrobDAOImpl();

        Grob grob = grobDAO.getGrobByRBR(rbr);

        String json = JSONUtils.fromPOJOToJSON(grob);

        return json.getBytes("UTF-8");
    }



}
