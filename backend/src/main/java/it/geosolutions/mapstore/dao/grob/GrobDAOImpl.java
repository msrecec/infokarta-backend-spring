package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.Grob;
import org.postgis.PGgeometry;
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
        String sql = "SELECT * FROM public.\"grobovi\" ORDER BY fid";
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, grobMapper);
        return grobovi;
    }

    @Override
    public List<Grob> getGroboviByGroblje(String groblje) {
        String sql = new StringBuilder().append("SELECT \"grobovi\".* FROM \"grobovi\" INNER JOIN \"groblja\" ON \"grobovi\".fk = \"groblja\".fid ").append("WHERE \"groblja\".\"naziv\" ILIKE ? ORDER BY \"grobovi\".\"Rednibroj\"").toString();
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, new Object[]{groblje} , grobMapper);
        return grobovi;
    }

    @Override
    public List<Grob> getRbrByGroblje(String groblje) {
        String sql = new StringBuilder().append("SELECT DISTINCT \"grobovi\".\"Rednibroj\" FROM \"grobovi\" INNER JOIN \"groblja\" ON \"grobovi\".fk = \"groblja\".fid\n").append("WHERE \"groblja\".naziv ILIKE ?").toString();

        List<Grob> grobovi = jdbcTemplateObject.query(sql, new Object[]{groblje}, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                Grob grob = new Grob();
                grob.setRedniBroj(rs.getString(1));
                return grob;
            }
        });

        return grobovi;
    }

    @Override
    public PGgeometry getGrobByFid(Integer fid) {
        String sql = "SELECT * from \"grobovi\" where \"grobovi\".\"fid\" = ? ";
        GrobMapper grobMapper = new GrobMapper();

        Grob grob = (Grob)jdbcTemplateObject.queryForObject(sql, new Object[]{fid} , grobMapper);

        return grob.getGeom();
    }
}
