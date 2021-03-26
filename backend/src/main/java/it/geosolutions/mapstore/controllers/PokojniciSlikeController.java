package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.config.FileSystemConfig;
import it.geosolutions.mapstore.DAO.PokojnikSlikaMeta.PokojnikSlikaMetaDAO;
import it.geosolutions.mapstore.DAO.PokojnikSlikaMeta.PokojnikSlikaMetaDAOImpl;
import it.geosolutions.mapstore.dto.PokojnikSlikaMetaDTO;
import it.geosolutions.mapstore.model.PokojnikSlikaMeta;
import it.geosolutions.mapstore.service.PokojnikSlikaMetaService;
import it.geosolutions.mapstore.service.PokojnikSlikaMetaServiceImpl;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.MIMETypeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
public class PokojniciSlikeController {


    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/upload/slika", method = RequestMethod.POST)
    @ResponseBody
    public byte[] handleFormUpload(
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file,
        @RequestParam("fidPokojnika") Integer fidPokojnika
    ) throws IOException {
        String lokacija;

        if (!file.isEmpty()) {
            PokojnikSlikaMetaDAO pokojnikSlikaMetaDAO = new PokojnikSlikaMetaDAOImpl();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if(MIMETypeUtil.isImage(extension)) {

                byte[] bytes = file.getBytes();

                lokacija = FileSystemConfig.ROOT_LOCATION+"\\Pokojnici"+"\\"+fidPokojnika;

                PokojnikSlikaMeta pokojnikSlikaMeta = new PokojnikSlikaMeta();

                pokojnikSlikaMeta.setNaziv(name);
                pokojnikSlikaMeta.setTip(extension);
                pokojnikSlikaMeta.setLokacija(lokacija);
                pokojnikSlikaMeta.setFk(fidPokojnika);

                pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlika(pokojnikSlikaMeta);

                File f = new File(lokacija);

                if(!f.exists()) {
                   if(!f.mkdir()){
                       return null;
                   }
                }

                FileOutputStream fos = new FileOutputStream(lokacija+"\\"+pokojnikSlikaMeta.getFid()+"."+extension);

                try {
                    fos.write(bytes);
                } finally {
                    fos.close();
                }

                return JSONUtils.fromPOJOToJSON(pokojnikSlikaMeta).getBytes(StandardCharsets.UTF_8);

            } else if(MIMETypeUtil.isDocument(extension)) {
                return null;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    @RequestMapping(value = "/pokojnici/download/slika/meta/{fid}", method = RequestMethod.GET)
    public void getImgMetaByImgMetaFid(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable Integer fid
    ) throws IOException {

        PokojnikSlikaMetaService pokojnikSlikaMetaService = new PokojnikSlikaMetaServiceImpl();

        PokojnikSlikaMetaDTO pokojnikSlikaMetaDTO = pokojnikSlikaMetaService.getSlikaMetaByFid(fid);

        byte[] bytes = JSONUtils.fromPOJOToJSON(pokojnikSlikaMetaDTO).getBytes(StandardCharsets.UTF_8);

        response.addHeader("Content-type", "application/json; charset=utf-8");

        response.getOutputStream().write(bytes);

        response.getOutputStream().flush();

    }

    @RequestMapping(value = "/pokojnici/download/slika/meta", method = RequestMethod.GET)
    public void getImgMetaByPokojnikFid(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam Integer fid
    ) throws IOException {

        PokojnikSlikaMetaService pokojnikSlikaMetaService = new PokojnikSlikaMetaServiceImpl();

        List<PokojnikSlikaMetaDTO> pokojnikSlikaMetaDTOList = pokojnikSlikaMetaService.getSlikaMetaByPokojnikFid(fid);

        byte[] bytes = JSONUtils.fromListToJSON(pokojnikSlikaMetaDTOList).getBytes(StandardCharsets.UTF_8);

        response.addHeader("Content-type", "application/json; charset=utf-8");

        response.getOutputStream().write(bytes);

        response.getOutputStream().flush();

    }

    @RequestMapping(value = "/pokojnici/download/slika", method = RequestMethod.GET)
    public void downloadImgResource(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam("fid") Integer fid,
        @RequestParam(value = "thumbnail", required = false) Boolean thumbnail
    ) throws IOException {
        PokojnikSlikaMetaDAO pokojnikSlikaMetaDAO = new PokojnikSlikaMetaDAOImpl();
        Optional<Boolean> oThumbnail = Optional.ofNullable(thumbnail);

        if(oThumbnail.isPresent()) {

        } else {

            PokojnikSlikaMeta pokojnikSlikaMeta = pokojnikSlikaMetaDAO.getSlikaMetaByFid(fid).get();

            File f = new File(
                pokojnikSlikaMeta.getLokacija()+"\\"
                    +pokojnikSlikaMeta.getFid()+"."
                    +pokojnikSlikaMeta.getTip());

            byte[] bytes = FileUtils.readFileToByteArray(f);

            response.setContentType(MIMETypeUtil.mimeTypes.get(pokojnikSlikaMeta.getTip()));
            response.addHeader("Content-Disposition", "attachment; filename="+pokojnikSlikaMeta.getNaziv()+"."+pokojnikSlikaMeta.getTip());
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();

        }
    }
}
