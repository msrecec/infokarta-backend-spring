package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.config.FileSystemConfig;
import it.geosolutions.mapstore.repository.PokojnikSlika.PokojnikSlikaMetaDAO;
import it.geosolutions.mapstore.repository.PokojnikSlika.PokojnikSlikaMetaDAOImpl;
import it.geosolutions.mapstore.model.PokojnikSlikaMeta;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.MIMETypeUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Controller
public class PokojniciSlikeController {

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/upload/slike", method = RequestMethod.POST)
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

    @RequestMapping(value = "/pokojnici/slika", method = RequestMethod.GET)
    public void downloadImgResource(HttpServletRequest request, HttpServletResponse response, @RequestParam("fid") Integer fid) throws IOException {
        PokojnikSlikaMetaDAO pokojnikSlikaMetaDAO = new PokojnikSlikaMetaDAOImpl();

        PokojnikSlikaMeta pokojnikSlikaMeta = pokojnikSlikaMetaDAO.getSlikaMetaByFid(fid);
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
