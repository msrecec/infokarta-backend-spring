package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.Rasvjeta;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
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
        String sql = "SELECT * FROM public.\"rasvjeta\" ORDER BY id_hist";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        List <Rasvjeta> rasvjeta = jdbcTemplateObject.query(sql, rasvjetaMapper);

        return rasvjeta;
    }

    @Override
    public List<Rasvjeta> findPaginated(Integer page) {
        Integer limit = DAO.pageSize;

        Integer offset = (page-1) * limit;

        String sql = "SELECT * FROM public.\"rasvjeta\" ORDER BY id_hist LIMIT ? OFFSET ? ";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        List <Rasvjeta> rasvjeta = jdbcTemplateObject.query(sql, new Object[]{limit, offset}, rasvjetaMapper);

        return rasvjeta;
    }

    @Override
    public void save(Rasvjeta rasvjeta) {

    }

    @Override
    public void update(Rasvjeta rasvjeta) {

    }

    @Override
    public Optional<Rasvjeta> findById(Integer id) {
        String sql = "SELECT * FROM public.\"rasvjeta\" WHERE id_hist = ? ";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        Optional<Rasvjeta> rasvjeta;

        try {
             rasvjeta = Optional.ofNullable((Rasvjeta) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, rasvjetaMapper));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            rasvjeta = Optional.ofNullable(null);
        }

        return rasvjeta;
    }

    public Integer findCount() {

        String sql = "SELECT COUNT(*) FROM public.\"rasvjeta\" ";
        Integer count;

        try {
            count = jdbcTemplateObject.queryForInt(sql);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            count = 0;
        }


        return count;
    }

}
