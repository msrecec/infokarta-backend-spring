package it.geosolutions.mapstore.DAO.Groblje;

import it.geosolutions.mapstore.DAO.Pokojnici.PokojnikMapper;
import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Groblje;
import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class GrobljeDAOImpl implements GrobljeDAO, JDBCConfig {
    private JdbcTemplate jdbcTemplateObject;

    public GrobljeDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Groblje> listGroblje() {
        String sql = "SELECT \"Groblje\", COUNT(*) FROM public.\"Grobovi\" GROUP BY \"Groblje\"";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        List <Groblje> groblja = jdbcTemplateObject.query(sql, grobljeMapper);
        return groblja;
    }
}
