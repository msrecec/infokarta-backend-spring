package it.geosolutions.mapstore.controllers;

import it.geosolutions.mapstore.DAO.SlikaMeta.SlikaMetaDAO;
import it.geosolutions.mapstore.DAO.SlikaMeta.SlikaMetaDAOImpl;
import it.geosolutions.mapstore.config.FileSystemConfig;
import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.SlikaMeta;
import it.geosolutions.mapstore.service.SlikaMetaService;
import it.geosolutions.mapstore.service.SlikaMetaServiceImpl;
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
public class SlikeController {


    //    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/pokojnici/upload/slika", method = RequestMethod.POST)
    @ResponseBody
    public byte[] handleFormUpload(
        @RequestParam("name") String name,
        @RequestParam("file") MultipartFile file,
        @RequestParam("fk") Integer fk
    ) throws IOException {
        String lokacija;

        if (!file.isEmpty()) {
            SlikaMetaDAO slikaMetaDAO = new SlikaMetaDAOImpl();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            if(MIMETypeUtil.isImage(extension)) {

                byte[] bytes = file.getBytes();

                lokacija = FileSystemConfig.ROOT_LOCATION+"\\Pokojnici"+"\\"+fk;

                SlikaMeta slikaMeta = new SlikaMeta();

                slikaMeta.setNaziv(name);
                slikaMeta.setTip(extension);
                slikaMeta.setLokacija(lokacija);
                slikaMeta.setFk(fk);

                slikaMeta = slikaMetaDAO.addSlika(slikaMeta);

                File f = new File(lokacija);

                if(!f.exists()) {
                   if(!f.mkdir()){
                       return null;
                   }
                }

                FileOutputStream fos = new FileOutputStream(lokacija+"\\"+ slikaMeta.getFid()+"."+extension);

                try {
                    fos.write(bytes);
                } finally {
                    fos.close();
                }

                return JSONUtils.fromPOJOToJSON(slikaMeta).getBytes(StandardCharsets.UTF_8);

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

        SlikaMetaService slikaMetaService = new SlikaMetaServiceImpl();

        SlikaMetaDTO slikaMetaDTO = slikaMetaService.getSlikaMetaByFid(fid);

        byte[] bytes = JSONUtils.fromPOJOToJSON(slikaMetaDTO).getBytes(StandardCharsets.UTF_8);

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

        SlikaMetaService slikaMetaService = new SlikaMetaServiceImpl();

        List<SlikaMetaDTO> slikaMetaDTOList = slikaMetaService.getSlikaMetaByPokojnikFid(fid);

        byte[] bytes = JSONUtils.fromListToJSON(slikaMetaDTOList).getBytes(StandardCharsets.UTF_8);

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
        SlikaMetaDAO slikaMetaDAO = new SlikaMetaDAOImpl();
        Optional<Boolean> oThumbnail = Optional.ofNullable(thumbnail);

        if(oThumbnail.isPresent()) {

        } else {

            Optional<SlikaMeta> oPokojnikSlikaMeta = slikaMetaDAO.getSlikaMetaByFid(fid);

            if(!oPokojnikSlikaMeta.isPresent()) {
                return;
            }

            SlikaMeta slikaMeta = slikaMetaDAO.getSlikaMetaByFid(fid).get();

            File f = new File(
                slikaMeta.getLokacija()+"\\"
                    + slikaMeta.getFid()+"."
                    + slikaMeta.getTip());

            byte[] bytes = FileUtils.readFileToByteArray(f);

            response.setContentType(MIMETypeUtil.mimeTypes.get(slikaMeta.getTip()));
//            response.addHeader("Content-Disposition", "attachment; filename="+slikaMeta.getNaziv()+"."+slikaMeta.getTip());
            response.addHeader("Content-Disposition", "inline; filename="+ slikaMeta.getNaziv()+"."+ slikaMeta.getTip());
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
