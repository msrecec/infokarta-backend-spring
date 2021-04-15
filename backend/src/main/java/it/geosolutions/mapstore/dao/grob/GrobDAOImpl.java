package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.grob.Grob;
import org.postgis.PGgeometry;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrobDAOImpl implements GrobDAO, JDBCConfig {
    private JdbcTemplate jdbcTemplateObject;

    public GrobDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource ds) {
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public Optional<Grob> findById(Integer id) {
        String sql = "SELECT * from \"grobovi\" where \"grobovi\".\"fid\" = ? ";
        GrobMapper grobMapper = new GrobMapper();
        Optional<Grob> grob;

        try {
             grob = Optional.ofNullable((Grob) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, grobMapper));
        } catch(EmptyResultDataAccessException e) {
            grob = Optional.ofNullable(null);
        }

        return grob;
    }



    @Override
    public List<Grob> findAll() {
        String sql = "SELECT * FROM public.\"grobovi\" ORDER BY fid";
        List<Grob> grobovi;

        GrobMapper grobMapper = new GrobMapper();
        try {
            grobovi = jdbcTemplateObject.query(sql, grobMapper);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            grobovi = new ArrayList<>();
        }

        return grobovi;
    }

    @Override
    public Integer findCount() {
        String sql = "SELECT COUNT(*) FROM public.\"grobovi\" ORDER BY fid";
        Integer count;

        try {
            count = jdbcTemplateObject.queryForInt(sql);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            count = 0;
        }

        return count;
    }

    @Override
    public List<Grob> findPaginated(Integer page) {
        Integer limit = DAO.pageSize;

        Integer offset = (page-1) * limit;

        String sql = "SELECT * FROM public.\"grobovi\" ORDER BY fid LIMIT ? OFFSET ? ";

        GrobMapper grobMapper = new GrobMapper();

        List<Grob> grobovi;

        try {

            grobovi = jdbcTemplateObject.query(sql, new Object[] {limit, offset}, grobMapper);

        } catch (EmptyResultDataAccessException e) {

            e.printStackTrace();

            grobovi = new ArrayList<>();

        }

        return grobovi;
    }

    /**
     * Footnote - no need to implement since this functionality
     * is handled by GeoServer - http://geoserver.org/
     *
     * @param grob
     * @return
     */

    @Override
    public Optional<Grob> save(Grob grob) {
        return Optional.empty();
    }


    /**
     * TODO - implement this later...
     *
     * @param grob
     * @return
     */

    @Override
    public Optional<Grob> update(Grob grob) {



        return Optional.empty();
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
        String sql = new StringBuilder().append("SELECT \"grobovi\".* FROM \"grobovi\" INNER JOIN \"groblja\" ON \"grobovi\".fk = \"groblja\".fid ").append("WHERE \"groblja\".\"naziv\" ILIKE ? ORDER BY \"grobovi\".\"redni_broj\"").toString();
        GrobMapper grobMapper = new GrobMapper();
        List<Grob> grobovi = jdbcTemplateObject.query(sql, new Object[]{groblje} , grobMapper);
        return grobovi;
    }

    @Override
    public List<Grob> getRbrByGroblje(String groblje) {
        String sql = new StringBuilder().append("SELECT DISTINCT \"grobovi\".\"redni_broj\" FROM \"grobovi\" INNER JOIN \"groblja\" ON \"grobovi\".fk = \"groblja\".fid\n").append("WHERE \"groblja\".naziv ILIKE ?").toString();

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
