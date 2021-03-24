package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.repository.PokojnikSlika.PokojnikSlikaDAO;
import it.geosolutions.mapstore.repository.PokojnikSlika.PokojnikSlikaDAOImpl;
import it.geosolutions.mapstore.model.PokojnikSlika;
import it.geosolutions.mapstore.utils.MIMETypeUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PokojniciSlikeController {

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFormUpload(
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!file.isEmpty()) {
            PokojnikSlikaDAO pokojnikSlikaDAO = new PokojnikSlikaDAOImpl();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if(MIMETypeUtil.isImage(extension)) {

                byte[] bytes = file.getBytes();

                PokojnikSlika pokojnikSlika = new PokojnikSlika();

                pokojnikSlika.setTip(extension);
                pokojnikSlika.setSlika(bytes);
                pokojnikSlika.setFk(1);

                return pokojnikSlikaDAO.addSlika(pokojnikSlika);

            } else {
                return "document success";
            }

        } else {
            return "failed to upload";
        }
    }

    @RequestMapping(value = "/pokojnici/slika", method = RequestMethod.GET)
    public void downloadImgResource(HttpServletRequest request, HttpServletResponse response, @RequestParam("fid") Integer fid) throws IOException {
        PokojnikSlikaDAO pokojnikSlikaDAO = new PokojnikSlikaDAOImpl();

        PokojnikSlika pokojnikSlika = pokojnikSlikaDAO.getSlikaByFid(fid);

        response.setContentType("image/jpeg");
        response.addHeader("Content-Disposition", "attachment; filename=mira.jpg");
        response.getOutputStream().write(pokojnikSlika.getSlika());
        response.getOutputStream().flush();
    }
}
