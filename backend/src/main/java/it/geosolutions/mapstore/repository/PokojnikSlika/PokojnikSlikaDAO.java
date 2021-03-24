package it.geosolutions.mapstore.repository.PokojnikSlika;

import it.geosolutions.mapstore.model.PokojnikSlika;

import javax.sql.DataSource;

public interface PokojnikSlikaDAO {
    public void setDataSource(DataSource ds);
    String addSlika(PokojnikSlika pokojnikSlika);
    PokojnikSlika getSlikaByFid(Integer fid);
}
