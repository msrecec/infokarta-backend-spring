package it.geosolutions.mapstore.DAO.PokojnikSlika;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.PokojnikSlikaMeta;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PokojnikSlikaMetaDAOImpl implements PokojnikSlikaMetaDAO, JDBCConfig{


    private JdbcTemplate jdbcTemplateObject;

    public PokojnikSlikaMetaDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public PokojnikSlikaMeta addSlika(PokojnikSlikaMeta pokojnikSlikaMeta) {
        PokojnikSlikaMetaMapper pokojnikSlikaMetaMapper = new PokojnikSlikaMetaMapper();
        String sql = "INSERT INTO public.\"Pokojnici_slike_meta\"(fid, naziv, lokacija, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *;";
        PokojnikSlikaMeta pokojnikSlikaMetaReturn = (PokojnikSlikaMeta)jdbcTemplateObject.queryForObject(sql, new Object[]{pokojnikSlikaMeta.getNaziv(), pokojnikSlikaMeta.getLokacija(), pokojnikSlikaMeta.getTip(), pokojnikSlikaMeta.getFk()}, pokojnikSlikaMetaMapper);
        return pokojnikSlikaMetaReturn;
    }

    @Override
    public PokojnikSlikaMeta getSlikaMetaByFid(Integer fid) {
        PokojnikSlikaMetaMapper pokojnikSlikaMetaMapper = new PokojnikSlikaMetaMapper();
        String sql = "SELECT * FROM public.\"Pokojnici_slike_meta\" WHERE public.\"Pokojnici_slike_meta\".fid = ? ";
        PokojnikSlikaMeta pokojnikSlikaMeta = (PokojnikSlikaMeta) jdbcTemplateObject.queryForObject(sql, new Object[]{fid}, pokojnikSlikaMetaMapper);
        return pokojnikSlikaMeta;
    }
}
