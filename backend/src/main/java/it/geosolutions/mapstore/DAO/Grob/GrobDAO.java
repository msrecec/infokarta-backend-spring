package it.geosolutions.mapstore.DAO.Grob;

import it.geosolutions.mapstore.model.Grob;

import javax.sql.DataSource;
import java.util.List;

public interface GrobDAO {
    public void setDataSource(DataSource ds);

    public List<Grob> listGrobovi();

    public List<Grob> getGroboviByGroblje(String groblje);

    public List<Grob> getRbrByGroblje(String groblje);

    public String getGeomByFid(Integer fid);
}
