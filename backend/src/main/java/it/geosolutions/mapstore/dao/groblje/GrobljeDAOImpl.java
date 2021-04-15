package it.geosolutions.mapstore.dao.groblje;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.dao.grob.GrobMapper;
import it.geosolutions.mapstore.model.grob.Grob;
import it.geosolutions.mapstore.model.groblje.Groblje;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

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
    public Optional<Groblje> findById(Integer id) {

        String sql = "SELECT * from \"groblja\" where \"groblja\".\"fid\" = ? ";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        Optional<Groblje> groblje;

        try {
            groblje = Optional.ofNullable((Groblje) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, grobljeMapper));
        } catch(EmptyResultDataAccessException e) {
            groblje = Optional.ofNullable(null);
        }

        return groblje;
    }

    @Override
    public List<Groblje> findAll() {
        String sql = "SELECT * FROM public.\"groblja\" ORDER BY fid";
        List <Groblje> groblja;
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        try {
            groblja = jdbcTemplateObject.query(sql, grobljeMapper);
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            groblja = new ArrayList();
        }
        return groblja;
    }

    @Override
    public Integer findCount() {
        Integer count;
        String sql = "SELECT * FROM public.\"groblja\" ORDER BY fid";
        try {
            count = jdbcTemplateObject.queryForInt(sql);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            count = 0;
        }
        return count;
    }

    @Override
    public List<Groblje> findPaginated(Integer page) {
        Integer limit = DAO.pageSize;

        Integer offset = (page-1) * limit;

        String sql = "SELECT * FROM public.\"groblja\" ORDER BY fid LIMIT ? OFFSET ? ";

        GrobljeMapper grobljeMapper = new GrobljeMapper();

        List <Groblje> groblja;

        try {

            groblja = jdbcTemplateObject.query(sql, new Object[] {limit, offset}, grobljeMapper);

        } catch (EmptyResultDataAccessException e) {

            e.printStackTrace();

            groblja = new ArrayList<>();

        }

        return groblja;
    }

    @Override
    public Optional<Groblje> save(Groblje groblje) {
        String sql = "INSERT INTO public.groblja(fid, naziv) VALUES (DEFAULT, ?) RETURNING *;";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        Optional<Groblje> oGroblje;

        try {
            oGroblje = Optional.ofNullable((Groblje)jdbcTemplateObject.queryForObject(sql, new Object[]{groblje.getNaziv()}, grobljeMapper));
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            oGroblje = Optional.ofNullable(null);
        }

        return oGroblje;
    }

    @Override
    public Optional<Groblje> update(Groblje groblje) {
        String sql = "UPDATE public.groblja SET naziv = ? WHERE public.groblja.fid = ? RETURNING *;";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        Optional<Groblje> oGroblje;

        try {
            oGroblje = Optional.ofNullable((Groblje)jdbcTemplateObject.queryForObject(sql,
                new Object[]{groblje.getNaziv(), groblje.getNaziv()}, grobljeMapper));
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            oGroblje = Optional.ofNullable(null);
        }

        return oGroblje;
    }

    @Override
    public List<Groblje> listGroblja() {
        String sql = "SELECT * FROM public.\"groblja\" ORDER BY fid";
        GrobljeMapper grobljeMapper = new GrobljeMapper();
        List <Groblje> groblja = jdbcTemplateObject.query(sql, grobljeMapper);
        return groblja;
    }

}
