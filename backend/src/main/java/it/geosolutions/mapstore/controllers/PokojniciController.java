package it.geosolutions.mapstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.geosolutions.mapstore.DAO.Pokojnik.PokojniciDAO;
import it.geosolutions.mapstore.DAO.Pokojnik.PokojniciDAOImpl;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;


@Controller
public class PokojniciController {

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnici(
        @RequestParam(value = "ime", required = false) String ime,
        @RequestParam(value = "prezime", required = false) String prezime,
        @RequestParam(value = "pocgodinaukopa", required = false) String pocGodinaUkopa,
        @RequestParam(value = "kongodinaukopa", required = false) String konGodinaUkopa,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "page", required = false) Integer page)
        throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        String jsonArray;
        Optional<String> oIme = Optional.ofNullable(ime);
        Optional<String> oPrezime = Optional.ofNullable(prezime);
        Optional<String> oPocGodinaUkopa = Optional.ofNullable(pocGodinaUkopa);
        Optional<String> oKonGodinaUkopa = Optional.ofNullable(konGodinaUkopa);
        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<Integer> oPage = Optional.ofNullable(page);

        jsonArray = pokojniciDAO.searchPokojnici(oIme, oPrezime, oPocGodinaUkopa,
            oKonGodinaUkopa, oGroblje, oPage);

        return jsonArray.getBytes("UTF-8");

    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPokojnik(@PathVariable Integer id) throws UnsupportedEncodingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();
        Optional<Integer> oId = Optional.ofNullable(id);

        Pokojnik pokojnik = pokojniciDAO.getPokojnikById(oId);

        String json = JSONUtils.fromPOJOToJSON(pokojnik);

        return json.getBytes("UTF-8");
    }

//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/columns", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getColumns(@RequestParam(value = "variables", required = false) Boolean variables) throws UnsupportedEncodingException {

        String json;

        Optional<Boolean> oVariables = Optional.ofNullable(variables);

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        if(oVariables.isPresent()) {
            Pokojnik pokojnik = pokojniciDAO.getFirstPokojnik();

            json = JSONUtils.fromPOJOToJSON(pokojnik);
        } else {
            List<String> columns = pokojniciDAO.listColumns();

            json = JSONUtils.fromListToJSON(columns);
        }


        return json.getBytes("UTF-8");
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/count", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getCount() throws UnsupportedEncodingException {

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Integer count = pokojniciDAO.getPokojnikCount();

        String json = "{" + "\"count\":"+ "\"" + count + "\"" +"}";

        return json.getBytes("UTF-8");
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.PUT)
    @ResponseBody
    public byte[] updatePokojnici(@RequestBody String json) throws UnsupportedEncodingException, JsonProcessingException {
        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = JSONUtils.fromJSONtoPOJO(json, Pokojnik.class);

        String outJson = pokojniciDAO.updatePokojnik(pokojnik);

        return outJson.getBytes("UTF-8");
    }

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici", method = RequestMethod.POST)
    @ResponseBody
    public byte[] addPokojnik(
        @RequestBody String json,
        @RequestParam(value = "groblje", required = false) String groblje,
        @RequestParam(value = "rbr", required = false) String rbr
    ) throws UnsupportedEncodingException, JsonProcessingException {

        String outJson;

        Optional<String> oGroblje = Optional.ofNullable(groblje);
        Optional<String> oRbr = Optional.ofNullable(rbr);

        PokojniciDAO pokojniciDAO = new PokojniciDAOImpl();

        Pokojnik pokojnik = JSONUtils.fromJSONtoPOJO(json, Pokojnik.class);

        if(oGroblje.isPresent() && oRbr.isPresent()) {

            outJson = pokojniciDAO.addPokojnikByGrobljeAndRbr(
                pokojnik,
                EncodingUtils.decodeISO88591(oGroblje.get()),
                EncodingUtils.decodeISO88591(oRbr.get())
            );

        } else {

            outJson = pokojniciDAO.addPokojnik(pokojnik);

        }


        return outJson.getBytes("UTF-8");
    }


    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/testu", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            System.out.println(bytes.toString());
            System.out.println(name);
            System.out.println("test success");
            // store the bytes somewhere
            return "success";
        } else {
            return "failure";
        }
    }



}
