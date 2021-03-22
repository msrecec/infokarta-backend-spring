package it.geosolutions.mapstore.DAO.PokojnikSlika;

import it.geosolutions.mapstore.pojo.PokojnikSlika;

import javax.sql.DataSource;

public interface PokojnikSlikaDAO {
    public void setDataSource(DataSource ds);
    String addSlika(PokojnikSlika pokojnikSlika);
    PokojnikSlika getSlikaByFid(Integer fid);
}
