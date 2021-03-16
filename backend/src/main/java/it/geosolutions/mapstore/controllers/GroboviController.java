package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Grob.GrobDAOImpl;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.DAO.Grob.GrobDAO;
import it.geosolutions.mapstore.pojo.Grob;
import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
public class GroboviController {
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getGrobovi(@RequestParam(value = "groblje", required = false) String groblje, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        GrobDAO grobDAO = new GrobDAOImpl();
        List<Grob> grobovi;
        Optional<String> oGroblje = Optional.ofNullable(groblje);

        if(oGroblje.isPresent()){
            String decoded =  new String(oGroblje.get().getBytes("iso-8859-1"), "UTF-8");
            System.out.println(decoded);
            grobovi = grobDAO.getGroboviByGroblje(decoded);
        } else {
            grobovi = grobDAO.listGrobovi();

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
