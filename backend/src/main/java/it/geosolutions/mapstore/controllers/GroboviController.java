package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.dao.grob.GrobDAOImpl;
import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.dto.EntityListDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTO;
import it.geosolutions.mapstore.dto.grobovi.GrobDTOWithoutGeom;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.service.grob.GrobServiceImpl;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class GroboviController {
    @Autowired
    GrobServiceImpl grobService;

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi", method = RequestMethod.GET)
    @ResponseBody
    public void getGrobovi(
        HttpServletRequest req,
        HttpServletResponse response,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "page", required = false) Integer page
    ) throws IOException {

        EntityListDTO groboviList;

        if(groblje != null && page == null){
            groboviList = grobService.findGroboviByGroblje(EncodingUtils.decodeISO88591(groblje));
        } else if (groblje == null && page != null) {
            groboviList = grobService.findPaginated(page);
        } else {
            groboviList = grobService.findAll();
        }

        HeaderUtils.responseWithJSON(response, JSONUtils.fromPOJOToJSON(groboviList));
    }


    @RequestMapping(value = "/grobovi/{fid}", method = RequestMethod.GET)
    public void getRasvjetaByIdHist(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("fid") Integer fid,
        @RequestParam(value = "geom", required = false) Boolean geom
    ) throws IOException {
        Optional<Boolean> oGeom = Optional.ofNullable(geom);

        String json = "{}";

        if(oGeom.isPresent()) {

            Optional<GrobDTO> grob = grobService.findById(fid);

            if(grob.isPresent()) {

                json = JSONUtils.fromPOJOToJSON(grob.get());

                response.setStatus(200);

            } else {

                response.setStatus(404);

            }

        } else {

            Optional<GrobDTOWithoutGeom> grob = grobService.findByIdWithoutGeom(fid);

            if(grob.isPresent()) {

                json = JSONUtils.fromPOJOToJSON(grob.get());

                response.setStatus(200);

            } else {

                response.setStatus(404);

            }

        }


        HeaderUtils.responseWithJSON(response, json);

    }

    @RequestMapping(value = "/test/grobovi", method = RequestMethod.GET)
    public void getGrobovi(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(value = "page", required = false) Integer page
    ) throws IOException {
        EntityListDTO grobovi;
        Optional<Integer> oPage = Optional.ofNullable(page);
        String json;

        if(oPage.isPresent()) {
            grobovi = grobService.findPaginated(1);
        } else {
            grobovi = grobService.findAll();
        }

        json = JSONUtils.fromPOJOToJSON(grobovi);

        HeaderUtils.responseWithJSON(response, json);
    }

}
