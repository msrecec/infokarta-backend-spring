package it.geosolutions.mapstore.repository.Grob;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.Grob;
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
    public List<Grob> getGroboviByGroblje(String groblje) {
        String sql = "SELECT \"Grobovi\".* FROM \"Grobovi\" INNER JOIN \"Groblja\" ON \"Grobovi\".fk = \"Groblja\".fid " +
            "WHERE \"Groblja\".\"naziv\" ILIKE ? ORDER BY \"Grobovi\".\"Rednibroj\"";
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, new Object[]{groblje} , grobMapper);
        return grobovi;
    }

    @Override
    public List<Grob> getRbrByGroblje(String groblje) {
        String sql = "SELECT DISTINCT \"Grobovi\".\"Rednibroj\" FROM \"Grobovi\" INNER JOIN \"Groblja\" ON \"Grobovi\".fk = \"Groblja\".fid\n" +
            "WHERE \"Groblja\".naziv ILIKE ?";

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
    public String getGeomByFid(Integer fid) {
        String sql = "SELECT ST_AsGeoJSON(\"Grobovi\".\"geom\") from \"Grobovi\" where \"Grobovi\".\"fid\" = ? ";

        String geom = (String)jdbcTemplateObject.queryForObject(sql, new Object[]{fid} , new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                String str = (String)rs.getObject(1);
                return str;
            }
        });

        return geom;
    }
}
