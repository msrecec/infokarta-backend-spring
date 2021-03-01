package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

public class PokojniciDAOImpl implements PokojniciDAO {

    private JdbcTemplate jdbcTemplateObject;

    public PokojniciDAOImpl() {
        JDBCConfig jdbcConfig = new JDBCConfig();
        this.jdbcTemplateObject = new JdbcTemplate(jdbcConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Pokojnik> listPokojnici() {
        String sql = "SELECT * FROM public.\"Pokojnici\"";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, pokojnikMapper);
        return pokojnici;
    }

    @Override
    public Pokojnik getPokojnik(Integer id) {
        String sql = "SELECT * FROM public.\"Pokojnici\" WHERE fid = ?";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        Pokojnik pokojnik = (Pokojnik) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, pokojnikMapper);
        return pokojnik;
    }
}
