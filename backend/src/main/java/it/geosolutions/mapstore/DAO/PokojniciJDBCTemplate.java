package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.api.Pokojnik;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class PokojniciJDBCTemplate implements PokojniciDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Pokojnik> listPokojnici() {
        String sql = "select * from geostore.pokojnici";
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, new PokojnikMapper());
        return pokojnici;
    }
}
