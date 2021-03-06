package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.rasvjeta.Rasvjeta;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import it.geosolutions.mapstore.utils.search.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RasvjetaDAOImpl implements RasvjetaDAO {
    @Autowired
    private SearchUtils<Rasvjeta> searchUtils;
    @Autowired
    @Qualifier("jdbcTemplateObjectFactory")
    private JdbcTemplate jdbcTemplateObject;

    public RasvjetaDAOImpl() {
    }

    @Override
    public List<Rasvjeta> findAll() {
        String sql = "SELECT * FROM public.\"rasvjeta\" ORDER BY geom";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        List <Rasvjeta> rasvjeta = jdbcTemplateObject.query(sql, rasvjetaMapper);

        return rasvjeta;
    }

    @Override
    public List<Rasvjeta> findPaginated(Integer page) {
        List<Rasvjeta> rasvjeta;
        Integer limit = DAO.pageSize;

        Integer offset = (page-1) * limit;

        String sql = "SELECT * FROM public.\"rasvjeta\" ORDER BY id_hist LIMIT ? OFFSET ? ";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        try {

            rasvjeta = jdbcTemplateObject.query(sql, new Object[]{limit, offset}, rasvjetaMapper);

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            rasvjeta = new ArrayList();
        }

        return rasvjeta;
    }

    /**
     * Footnote - no need to implement since this functionality
     * is handled by GeoServer - http://geoserver.org/
     *
     * @param rasvjeta
     * @return
     */

    @Override
    public Optional<Rasvjeta> save(Rasvjeta rasvjeta) {
        return Optional.empty();
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

            oRasvjeta = Optional.ofNullable(jdbcTemplateObject.queryForObject(sql, params, mapper));

        } catch (EmptyResultDataAccessException e) {

            oRasvjeta = Optional.empty();

        }


        return oRasvjeta;
    }

    @Override
    public Optional<Rasvjeta> findById(Integer id) {
        String sql = "SELECT * FROM public.\"rasvjeta\" WHERE id_hist = ? ";

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        Optional<Rasvjeta> rasvjeta;

        try {
             rasvjeta = Optional.ofNullable(jdbcTemplateObject.queryForObject(sql, new Object[]{id}, rasvjetaMapper));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            rasvjeta = Optional.empty();
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

    @Override
    public List<Rasvjeta> fullSearch(Map<String, Object> params, SearchEntity entity, Integer page, List<SearchEntity> orderedEntities) {

        RasvjetaMapper rasvjetaMapper = new RasvjetaMapper();

        List<Rasvjeta> rasvjeta = searchUtils.fullSearchList(params, entity, page, DAO.pageSize, rasvjetaMapper, orderedEntities);

        return rasvjeta;
    }

    @Override
    public Integer fullSearchCount(Map<String, Object> params, SearchEntity entity, List<SearchEntity> orderedEntities) {

        Integer count = searchUtils.fullSearchListCount(params, entity, orderedEntities);

        return count;
    }
}
