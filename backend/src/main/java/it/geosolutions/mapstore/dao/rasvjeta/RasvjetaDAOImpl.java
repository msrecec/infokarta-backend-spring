package it.geosolutions.mapstore.dao.rasvjeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.model.Pokojnik;
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
//        String sql = "UPDATE \"pokojnici\" SET \"IME I PREZIME\" = ?, \"Prezime djevojačko\" = ?, \"IME OCA\" = ?, \"NADIMAK\" = ?, \"OIB\" = ?, \"SPOL\" = ?, \"DATU ROĐENJA\" = ?, \n" +
//            "\"Bračno stanje\" = ?, \"MJESTO STANOVANJA\" = ?, \"ADRESA STANOVANJA\" = ?, \"Ime i prezime bračnog druga\" = ?, \"DOB\" = ?, \"UZROK SMRTI\" = ?,\n" +
//            "\"Mjesto smrti\" = ?, \"DATUM SMRTI\" = ?, \"DATUM KREMIRANJA\" = ?, \"DATUM UKOPA\" = ?, \"oznaka grobnice\" = ?, \"groblje\" = ?, \"Naknadni upisi i bilješke\" = ?,\n" +
//            "\"Godina ukopa\" = ?, \"USLUGA\" = ?, \"RAČUN\" = ?, \"DATUM USLUGE\" = ?, \"IME\" = ?, \"PREZIME\" = ? WHERE fid = ? RETURNING *";
//
//        Rasvjeta rasvjeta = jdbcTemplateObject.update(sql, pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
//            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
//            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
//            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
//            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getFid());
//
//        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";

        return null;
    }

    @Override
    public Optional<Rasvjeta> update(Rasvjeta rasvjeta) {
        return null;
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
