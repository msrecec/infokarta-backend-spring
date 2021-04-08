package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.model.Grob;
import org.postgis.PGgeometry;

import javax.sql.DataSource;
import java.util.List;

public interface GrobDAO {
    public void setDataSource(DataSource ds);

    public List<Grob> listGrobovi();

    public List<Grob> getGroboviByGroblje(String groblje);

    public List<Grob> getRbrByGroblje(String groblje);

    public PGgeometry getGrobByFid(Integer fid);
}
