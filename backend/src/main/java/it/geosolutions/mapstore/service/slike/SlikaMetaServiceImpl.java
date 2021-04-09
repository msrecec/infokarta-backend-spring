package it.geosolutions.mapstore.service.slike;

import it.geosolutions.mapstore.dao.slika.SlikaMetaDAO;
import it.geosolutions.mapstore.dao.slika.SlikaMetaDAOImpl;
import it.geosolutions.mapstore.dto.SlikaMetaDTO;
import it.geosolutions.mapstore.model.SlikaMeta;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlikaMetaServiceImpl implements SlikaMetaService {

    private SlikaMetaDAO slikaMetaDAO;

    public SlikaMetaServiceImpl() {
        this.slikaMetaDAO = new SlikaMetaDAOImpl();
    }

    @Override
    public SlikaMetaDTO getSlikaMetaByFid(Integer fid, String entity) {

        Optional<SlikaMeta> oPokojnikSlikaMeta = slikaMetaDAO.getSlikaMetaByFid(fid, entity);

        if(oPokojnikSlikaMeta.isPresent()) {
            return mapSlikaMetaToDTO(oPokojnikSlikaMeta.get());
        } else {
            return null;
        }
    }

    @Override
    public List<SlikaMetaDTO> getSlikaMetaByEntityFid(Integer fid, String entity) {
        List<SlikaMeta> slikaMetaList = slikaMetaDAO.getSlikaMetaByEntityFid(fid, entity);
        List<SlikaMetaDTO> slikaMetaDTOList = new ArrayList<>();

        for(SlikaMeta p : slikaMetaList) {
            slikaMetaDTOList.add(mapSlikaMetaToDTO(p));
        }

        return slikaMetaDTOList;
    }

    private SlikaMetaDTO mapSlikaMetaToDTO(SlikaMeta slikaMeta) {
        return new SlikaMetaDTO(slikaMeta.getFid(), slikaMeta.getNaziv(), slikaMeta.getTip(), slikaMeta.getFk());
    }

    @Override
    public ByteArrayOutputStream createThumbnail(MultipartFile originalFile, Integer width) throws IOException {
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(originalFile.getInputStream());
        img = Scalr.rotate(img, Scalr.Rotation.CW_90, Scalr.OP_ANTIALIAS);
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbImg, originalFile.getContentType().split("/")[1] , thumbOutput);
        return thumbOutput;
    }

    @Override
    public SlikaMetaDTO addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity) {
        return mapSlikaMetaToDTO(slikaMetaDAO.addSlikaMetaByEntity(slikaMeta, entity));
    }
}
