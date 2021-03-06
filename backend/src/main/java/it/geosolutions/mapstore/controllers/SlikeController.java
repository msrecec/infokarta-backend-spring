package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.config.fileSystem.FileSystemConfig;
import it.geosolutions.mapstore.dao.slika.SlikaMetaDAO;
import it.geosolutions.mapstore.dto.slika.SlikaMetaDTO;
import it.geosolutions.mapstore.model.slikaMeta.SlikaMeta;
import it.geosolutions.mapstore.service.slike.SlikaMetaService;
import it.geosolutions.mapstore.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
public class SlikeController {
    @Autowired
    SlikaMetaDAO slikaMetaDAO;
    @Autowired
    SlikaMetaService slikaMetaService;

    /**
     * TODO - Implement Buffered write instead of Write
     *
     * Adds metadata for images in upload, writes the image in original format into the file system and compresses
     * the original into required format as a thumbnail and writes the thumbnail into file system.
     *
     * TODO - Break apart into a different module
     *
     * @param request
     * @param response
     * @param file upload file
     * @param inEntity entity name that the file is being uploaded for
     * @param entityFid id of entity that the file is being uploaded for
     * @throws IOException
     */

    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/{entity}/upload/media/slika", method = RequestMethod.POST)
    @ResponseBody
    public void addSlikaByEntity(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam("file") MultipartFile file,
        @PathVariable("entity") String inEntity,
        @RequestParam("entityFid") Integer entityFid
    ) throws IOException {

        String root = FileSystemConfig.ROOT_LOCATION;

        String entity = EncodingUtils.decodeISO88591(inEntity).toLowerCase();

        if(EntityUtil.isEntity(entity)) {

            if (!file.isEmpty()) {

                String extension = FilenameUtils.getExtension(file.getOriginalFilename());

                String name = FilenameUtils.getName(file.getOriginalFilename());

                byte[] bytes = file.getBytes();

                if(MIMETypeUtil.isImage(extension)) {

                    InputStream stream = file.getInputStream();

                    String contentType = MIMETypeUtil.mimeTypes.get(extension);

                    SlikaMetaDTO slikaMetaDTO;

                    SlikaMeta slikaMeta = new SlikaMeta();

                    /**
                     * Full file system addresses
                     *
                     */

                    String entityFolder = root + "\\" + entity;
                    String mediaFolder = root + "\\" + entity + "\\media";
                    String slikeFolder = root + "\\" + entity + "\\media\\slike";

                    /**
                     * Relative file system addresses
                     *
                     */

                    String relativeSlikaFolder = entity + "\\" + "media" + "\\" + "slike" + "\\" + entityFid;
                    String relativeOriginalFolder = relativeSlikaFolder + "\\" + "original";
                    String relativeThumbnailFolder = relativeSlikaFolder + "\\" + "thumbnail";

                    /**
                     * Store metadata about images
                     *
                     */

                    slikaMeta.setNaziv(name);
                    slikaMeta.setTip(extension);
                    slikaMeta.setOriginal(relativeOriginalFolder);
                    slikaMeta.setThumbnail(relativeThumbnailFolder);
                    slikaMeta.setFk(entityFid);

                    slikaMetaDTO = slikaMetaService.addSlikaMetaByEntity(slikaMeta, entity);


                    /**
                     * Full file system addresses
                     *
                     */

                    String slikaFolder = root + "\\" + relativeSlikaFolder;
                    String originalFolder = root + "\\" + relativeOriginalFolder;
                    String thumbnailFolder = root + "\\" + relativeThumbnailFolder;

                    /**
                     *  entity folder check (if !exist create)
                     */

                    File f = new File(entityFolder);

                    if(!f.exists()) {
                        if(!f.mkdir()){
                            return;
                        }
                    }

                    /**
                     * media folder check (if !exist create)
                     */

                    f = new File(mediaFolder);

                    if(!f.exists()) {
                        if(!f.mkdir()){
                            return;
                        }
                    }

                    /**
                     * images folder check (if !exist create)
                     */

                    f = new File(slikeFolder);

                    if(!f.exists()) {
                        if(!f.mkdir()){
                            return;
                        }
                    }

                    /**
                     * specific image folder check (if !exist create)
                     */

                    f = new File(slikaFolder);

                    if(!f.exists()) {
                       if(!f.mkdir()){
                           return;
                       }
                    }

                    /**
                     * originals check (if !exist create)
                     */

                    f = new File(originalFolder);

                    if(!f.exists()) {
                        if(!f.mkdir()){
                            return;
                        }
                    }

                    /**
                     * TODO - Implement Buffered write instead of Write
                     *
                     * write original file to folder 'original'
                     */

                    FileOutputStream fos = new FileOutputStream(originalFolder + "\\" + slikaMetaDTO.getFid() + "." + extension);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }

                    /**
                     * TODO - Implement Buffered write instead of Write
                     *
                     * thumbnails check (if !exist create)
                     */

                    f = new File(thumbnailFolder);

                    if(!f.exists()) {
                        if(!f.mkdir()){
                            return;
                        }
                    }

                    /**
                     * write thumbnail file to folder 'thumbnail'
                     */

                    ByteArrayOutputStream thumbnailArr = slikaMetaService.createThumbnail(stream, contentType,100);

                    fos = new FileOutputStream(thumbnailFolder + "\\" + slikaMetaDTO.getFid() + "." + extension);

                    try {
                        fos.write(thumbnailArr.toByteArray());
                    } finally {
                        fos.close();
                    }

                    /**
                     * Respond with metadata
                     */

                    HeaderUtils.responseWithJSON(response, JSONUtils.fromPOJOToJSON(slikaMetaDTO));

                } else {
                    response.setStatus(415);
                    return;
                }

            } else {
                response.setStatus(400);
                return;
            }
        }
        response.setStatus(400);
        return;
    }

    /**
     *
     * @param request
     * @param response
     * @param inEntity entity name that the file is being uploaded for
     * @param fid id of media file metadata
     * @throws IOException
     */

    @RequestMapping(value = "/{entity}/download/media/slika/meta/{fid}", method = RequestMethod.GET)
    public void getSlikaMetaByFid(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("entity") String inEntity,
        @PathVariable("fid") Integer fid
    ) throws IOException {

        String entity = EncodingUtils.decodeISO88591(inEntity).toLowerCase();

        if(EntityUtil.isEntity(entity)) {

            SlikaMetaDTO slikaMetaDTO = slikaMetaService.getSlikaMetaByFid(fid, entity);

            String json = JSONUtils.fromPOJOToJSON(slikaMetaDTO);

            HeaderUtils.responseWithJSON(response, json);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param inEntity entity name that the file is being uploaded for
     * @param fid id of media file metadata
     * @throws IOException
     */

    @RequestMapping(value = "/{entity}/download/media/slika/meta", method = RequestMethod.GET)
    public void getImgMetaByEntityFid(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("entity") String inEntity,
        @RequestParam("entityFid") Integer fid
    ) throws IOException {

        String entity = EncodingUtils.decodeISO88591(inEntity).toLowerCase();

        if(EntityUtil.isEntity(entity)) {

            List<SlikaMetaDTO> slikaMetaDTOList = slikaMetaService.getSlikaMetaByEntityFid(fid, entity);

            HeaderUtils.responseWithJSON(response, JSONUtils.fromListToJSON(slikaMetaDTOList));

        }
    }

    /**
     *
     * @param request
     * @param response
     * @param inEntity entity name that the file is being uploaded for
     * @param fid id of media file metadata
     * @param hasThumbnail if you want thumbnail then true if original then false
     * @throws IOException
     */

    @RequestMapping(value = "/{entity}/download/media/slika/{fid}", method = RequestMethod.GET)
    public void downloadImgResource(
        HttpServletRequest request,
        HttpServletResponse response,
        @PathVariable("entity") String inEntity,
        @PathVariable("fid") Integer fid,
        @RequestParam(value = "thumbnail", required = false) Boolean hasThumbnail
    ) throws IOException {
        Optional<Boolean> oThumbnail = Optional.ofNullable(hasThumbnail);
        String entity = EncodingUtils.decodeISO88591(inEntity).toLowerCase();

        if(EntityUtil.isEntity(entity)) {

            Optional<SlikaMeta> oPokojnikSlikaMeta = slikaMetaDAO.getSlikaMetaByFid(fid, entity);

            if (!oPokojnikSlikaMeta.isPresent()) {
                response.setStatus(404);
                HeaderUtils.responseWithJSON(response, "[]");
                return;
            }

            SlikaMeta slikaMeta = oPokojnikSlikaMeta.get();
            File f;

            if (oThumbnail.isPresent()) {

                String thumbnail = FileSystemConfig.ROOT_LOCATION + "\\" + slikaMeta.getThumbnail();

                f = new File(
                    thumbnail
                        + "\\" + slikaMeta.getFid()
                        + "." + slikaMeta.getTip());

            } else {

                String original = FileSystemConfig.ROOT_LOCATION + "\\" + slikaMeta.getOriginal();

                f = new File(
                    original
                        + "\\" + slikaMeta.getFid()
                        + "." + slikaMeta.getTip());
            }


            if (!f.exists()) {
                response.setStatus(404);
                HeaderUtils.responseWithJSON(response, "[]");
                return;
            }

            byte[] bytes = FileUtils.readFileToByteArray(f);

            response.setContentType(MIMETypeUtil.mimeTypes.get(slikaMeta.getTip()));

            /**
             * "Content-Disposition": "attachment;" for direct download rather than inline display
             */

            response.addHeader("Content-Disposition", "inline; filename=" + slikaMeta.getNaziv() + "." + slikaMeta.getTip());
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();

        }
    }

/*
    @RequestMapping(value="/grobovi/test/transfer", method = RequestMethod.POST)
    public void transferImages(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        GrobDAO grobDAO = new GrobDAOImpl();
        String naziv;
        String[] nazivArr;
        SlikaMeta pokojnikSlikaMeta;
        String src;

        List<Grob> grobovi = grobDAO.listGrobovi();

        String lokacija = FileSystemConfig.ROOT_LOCATION + "\\Grobovi_slike\\";

        SlikaMetaDAO pokojnikSlikaMetaDAO = new SlikaMetaDAOImpl();

        for(Grob grob : grobovi) {
            if(Optional.ofNullable(grob.getSource()).isPresent() && grob.getSource().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource().replace("\\", "/");
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource1()).isPresent() && grob.getSource1().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource1();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource1().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource2()).isPresent() && grob.getSource2().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource2();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource2().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource3()).isPresent() && grob.getSource3().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource3();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource3().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource4()).isPresent() && grob.getSource4().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource4();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource4().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource5()).isPresent() && grob.getSource5().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource5();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource5().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource6()).isPresent() && grob.getSource6().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource6();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource6().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
            if(Optional.ofNullable(grob.getSource7()).isPresent() && grob.getSource7().contains("PrimostenGIS")) {
                pokojnikSlikaMeta = new SlikaMeta();
                src = grob.getSource7();
                File unos = new File(FileSystemConfig.TEST_ROOT_LOCATION+"\\"+src);

                if(unos.exists()) {
                    nazivArr = grob.getSource7().split("/");
                    naziv = nazivArr[nazivArr.length - 1];
                    naziv = naziv.replace(".jpg", "");
                    if(naziv.contains("\\")) {
                        nazivArr = naziv.split("\\\\");
                        naziv = nazivArr[nazivArr.length - 1];
                    }
                    pokojnikSlikaMeta.setNaziv(naziv);
                    pokojnikSlikaMeta.setTip("jpg");
                    pokojnikSlikaMeta.setLokacija("Grobovi_slike\\" + grob.getFid());
                    pokojnikSlikaMeta.setFk(grob.getFid());

                    pokojnikSlikaMeta = pokojnikSlikaMetaDAO.addSlikaGrob(pokojnikSlikaMeta);

                    File f = new File(lokacija + grob.getFid());

                    if (!f.exists()) {
                        if (!f.mkdir()) {
                            return;
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(lokacija + grob.getFid() + "\\" + pokojnikSlikaMeta.getFid() + ".jpg");

    //                if(src.contains("%")) {
    //                    src = src.replace("%", " ");
    //                }

                    byte[] bytes = FileUtils.readFileToByteArray(unos);

                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                }
            }
        }
    }*/
}
