package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.Grob.GrobDAOImpl;
import it.geosolutions.mapstore.DAO.Groblje.GrobljeDAOImpl;
import it.geosolutions.mapstore.DAO.Grob.GrobDAO;
import it.geosolutions.mapstore.pojo.Grob;
import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.postgis.PGgeometry;
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
    public byte[] getGrobovi(
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "geom", required = false) Boolean geom,
        @RequestParam(value = "fid", required = false) Integer fid
    ) throws UnsupportedEncodingException {

        GrobDAO grobDAO = new GrobDAOImpl();
        List<Grob> grobovi;
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Boolean> oGeom = Optional.ofNullable(geom);
        Optional<Integer> oFid = Optional.ofNullable(fid);
        String json;

        if(oGroblje.isPresent()){
            String decoded = EncodingUtils.decodeISO88591(oGroblje.get());
            grobovi = grobDAO.getGroboviByGroblje(decoded);
            json = JSONUtils.fromListToJSON(grobovi);
        } else if (oGeom.isPresent() && oFid.isPresent() && !oGroblje.isPresent()) {

            json = grobDAO.getGeomByFid(oFid.get());

        } else {
            grobovi = grobDAO.listGrobovi();
            json = JSONUtils.fromListToJSON(grobovi);
        }

        return json.getBytes("UTF-8");
    }


}
