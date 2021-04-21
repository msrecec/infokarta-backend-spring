package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.grob.Grob;
import org.postgis.PGgeometry;

import javax.sql.DataSource;
import java.util.List;

public interface GrobDAO extends DAO<Grob> {

    public List<Grob> listGrobovi();

    public List<Grob> getGroboviByGroblje(String groblje);

    public List<Grob> getRbrByGroblje(String groblje);

    public PGgeometry getGrobByFid(Integer fid);

    public Grob getGrobByPokojnikFid(Integer fid);
}
