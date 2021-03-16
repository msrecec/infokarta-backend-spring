package it.geosolutions.mapstore.DAO.Grob;

import it.geosolutions.mapstore.pojo.Grob;

import javax.sql.DataSource;
import java.util.List;

public interface GrobDAO {
    public void setDataSource(DataSource ds);

    public List<Grob> listGrobovi();

    public Grob getGrobByRBR(String redniBroj);

    public List<Grob> getRBRGrobova();

    public List<Grob> getRBRGrobovaByGroblje(String groblje);
}
