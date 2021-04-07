package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.groblje.GrobljeMapper;
import it.geosolutions.mapstore.model.Groblje;
import it.geosolutions.mapstore.model.Rasvjeta;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RasvjetaDAOImpl implements RasvjetaDAO, JDBCConfig {
    private JdbcTemplate jdbcTemplateObject;

    public RasvjetaDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource ds) {
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public List<Rasvjeta> findAll() {
        String sql = "SELECT geom, fid, \"Materijal\", \"Stanje\", source, mjerno_mjesto, vod, kategorija, " +
            "vrsta_rasvjetnog_mjesta, razdjelnik, trosilo, vrsta_svjetiljke, broj_svjetiljki, grlo, vrsta_stakla, " +
            "polozaj_kabela, godina_izgradnje, oznaka_ugovora, id_hist, time_start, time_end, user_role " +
            "FROM public.\"rasvjeta\" ORDER BY id_hist";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        List <Rasvjeta> rasvjeta = jdbcTemplateObject.query(sql, rasvjetaMapper);

        return rasvjeta;
    }

    @Override
    public Rasvjeta findByIdHist(Integer idHist) {
        String sql = "SELECT geom, fid, \"Materijal\", \"Stanje\", source, mjerno_mjesto, vod, kategorija, " +
            "vrsta_rasvjetnog_mjesta, razdjelnik, trosilo, vrsta_svjetiljke, broj_svjetiljki, grlo, vrsta_stakla, " +
            "polozaj_kabela, godina_izgradnje, oznaka_ugovora, id_hist, time_start, time_end, user_role " +
            "FROM public.\"rasvjeta\" WHERE id_hist = ?";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        Rasvjeta rasvjeta = (Rasvjeta) jdbcTemplateObject.queryForObject(sql, new Object[]{idHist}, rasvjetaMapper);

        return rasvjeta;
    }
}
