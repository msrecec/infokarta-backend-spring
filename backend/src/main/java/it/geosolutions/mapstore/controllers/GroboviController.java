package it.geosolutions.mapstore.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.geosolutions.mapstore.dao.grob.GrobDAOImpl;
import it.geosolutions.mapstore.dao.grob.GrobDAO;
import it.geosolutions.mapstore.model.Grob;
import it.geosolutions.mapstore.serializers.PGgeometrySerializer;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.HeaderUtils;
import org.postgis.PGgeometry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class GroboviController {
    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/grobovi", method = RequestMethod.GET)
    @ResponseBody
    public void getGrobovi(
        HttpServletRequest req,
        HttpServletResponse response,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "geom", required = false) Boolean geom,
        @RequestParam(value = "fid", required = false) Integer fid
    ) throws IOException {

        GrobDAO grobDAO = new GrobDAOImpl();
        List<Grob> grobovi;
        PGgeometry geometry = null;
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Boolean> oGeom = Optional.ofNullable(geom);
        Optional<Integer> oFid = Optional.ofNullable(fid);
        String json;

        if(oGroblje.isPresent()){
            String decoded = EncodingUtils.decodeISO88591(oGroblje.get());
            grobovi = grobDAO.getGroboviByGroblje(decoded);
            json = JSONUtils.fromListToJSON(grobovi);
        } else if (oGeom.isPresent() && oFid.isPresent() && !oGroblje.isPresent()) {

            if(oFid.get() > 0) {

                geometry = grobDAO.getGrobByFid(oFid.get());
                json = JSONUtils.fromPGgeometryToJSON(geometry);

            } else {
                return;
            }

        } else {
            grobovi = grobDAO.listGrobovi();
            json = JSONUtils.fromListToJSON(grobovi);
        }

        HeaderUtils.responseWithJSON(response, json);
    }


}
