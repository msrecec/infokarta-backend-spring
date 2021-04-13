package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
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
    public Optional<Rasvjeta> save(Rasvjeta rasvjeta) {
        return null;
    }

    @Override
    public Optional<Rasvjeta> update(Rasvjeta rasvjeta) {

        Optional<Rasvjeta> oRasvjeta;

        RasvjetaMapper mapper = new RasvjetaMapper();

        Object[] params = new Object[] {
            rasvjeta.getMaterijal(), rasvjeta.getStanje(), rasvjeta.getSource(), rasvjeta.getMjernoMjesto(), rasvjeta.getVod(), rasvjeta.getKategorija(),
            rasvjeta.getVrstaRasvjetnogMjesta(), rasvjeta.getRazdjelnik(), rasvjeta.getTrosilo(), rasvjeta.getVrstaSvjetiljke(), rasvjeta.getBrojSvjetiljki(),
            rasvjeta.getGrlo(), rasvjeta.getVrstaStakla(), rasvjeta.getPolozajKabela(), rasvjeta.getGodinaIzgradnje(), rasvjeta.getOznakaUgovora(),
            rasvjeta.getTimeStart(), rasvjeta.getTimeEnd(), rasvjeta.getUserRole(), rasvjeta.getIdHist()
        };

        String sql = "UPDATE public.rasvjeta\n" +
            "\tSET \"Materijal\"=?, \"Stanje\"=?, source=?, mjerno_mjesto=?, vod=?, kategorija=?, vrsta_rasvjetnog_mjesta=?, razdjelnik=?," +
            " trosilo=?, vrsta_svjetiljke=?, broj_svjetiljki=?, " +
            "grlo=?, vrsta_stakla=?, polozaj_kabela=?, godina_izgradnje=?, oznaka_ugovora=?, time_start=?, time_end=?, user_role=?\n" +
            "\tWHERE id_hist=? RETURNING *";

        try {

            oRasvjeta = Optional.ofNullable((Rasvjeta)jdbcTemplateObject.queryForObject(sql, params, mapper));

        } catch (EmptyResultDataAccessException e) {

            oRasvjeta = Optional.ofNullable(null);

        }


        return oRasvjeta;
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
