package it.geosolutions.mapstore.DAO.Grob;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Grob;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GrobDAOImpl implements GrobDAO {
    private JdbcTemplate jdbcTemplateObject;

    public GrobDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource ds) {
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public List<Grob> listGrobovi() {
        String sql = "SELECT * FROM public.\"Grobovi\" ORDER BY fid";
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, grobMapper);
        return grobovi;
    }

    @Override
    public Grob getGrobByRBR(String redniBroj) {
        String rbr = redniBroj.trim();
        String sql = "SELECT * FROM public.\"Grobovi\" WHERE TRIM(public.\"Grobovi\".\"Rednibroj\") ILIKE ? ORDER BY fid";
        GrobMapper grobMapper = new GrobMapper();
        Grob grob = (Grob)jdbcTemplateObject.queryForObject(sql, new Object[]{rbr}, grobMapper);

        return grob;
    }

    @Override
    public List<Grob> getGroboviByGroblje(String groblje) {
        String sql = "SELECT * FROM \"Grobovi\" INNER JOIN \"Groblja\" ON \"Grobovi\".fk = \"Groblja\".fid WHERE TRIM(\"Groblja\".naziv) ILIKE ? ORDER BY \"Rednibroj\"";
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, new Object[]{groblje} , grobMapper);
        return grobovi;
    }
}
