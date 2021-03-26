package it.geosolutions.mapstore.DAO.Groblje;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.Groblje;
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
    public List<Groblje> listGroblja() {
        String sql = "SELECT * FROM public.\"Groblja\" ORDER BY fid";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        List <Groblje> groblja = jdbcTemplateObject.query(sql, grobljeMapper);
        return groblja;
    }

}
