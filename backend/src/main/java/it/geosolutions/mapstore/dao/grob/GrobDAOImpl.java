package it.geosolutions.mapstore.dao.grob;

import it.geosolutions.mapstore.config.jdbc.JdbcConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.grob.Grob;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GrobDAOImpl implements GrobDAO {
    @Autowired
    @Qualifier("jdbcTemplateObjectFactory")
    private JdbcTemplate jdbcTemplateObject;

    public GrobDAOImpl() {
    }

    @Override
    public Optional<Grob> findById(Integer id) {
        String sql = "SELECT * from \"grobovi\" where \"grobovi\".\"fid\" = ? ";
        GrobMapper grobMapper = new GrobMapper();
        Optional<Grob> grob;

        try {
             grob = Optional.ofNullable((Grob) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, grobMapper));
        } catch(EmptyResultDataAccessException e) {
            grob = Optional.empty();
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
        String sql = "SELECT COUNT(*) FROM public.\"grobovi\"";
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


    @Override
    public Optional<Grob> update(Grob grob) {
        String sql = "UPDATE public.grobovi\n" +
            "\tSET source=?, source1=?, source2=?, source3=?, source4=?, source5=?, source6=?, source7=?, " +
            "redni_broj=?, grobnica=?, broj_lezaja=?, groblje=?, fk=? WHERE public.grobovi.fid = ? RETURNING *";

        Object[] params = new Object[] {
          grob.getSource(),grob.getSource1(),grob.getSource2(),grob.getSource3(),grob.getSource4(),grob.getSource5(),
            grob.getSource6(),grob.getSource7(), grob.getRedniBroj(), grob.getGrobnica(), grob.getBrojLezaja(),
            grob.getGroblje(), grob.getFk(), grob.getFid()
        };

        GrobMapper grobMapper = new GrobMapper();

        Optional<Grob> oGrob;

        try {
            oGrob = Optional.ofNullable((Grob)jdbcTemplateObject.queryForObject(sql, params, grobMapper));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            oGrob = Optional.empty();
        }

        return oGrob;
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

    @Override
    public Grob getGrobByPokojnikFid(Integer fid) {
        String sql = "SELECT * FROM \"grobovi\" INNER JOIN \"pokojnici\" ON \"grobovi\".fid = pokojnici.fk WHERE \"pokojnici\".fid = ?";
        GrobMapper grobMapper = new GrobMapper();
        Grob grob;

        try {
            grob = (Grob)jdbcTemplateObject.queryForObject(sql, new Object[]{fid}, grobMapper);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            grob = new Grob();
        }

        return grob;
    }
}
