package it.geosolutions.mapstore.DAO.SlikaMeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.SlikaMeta;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SlikaMetaDAOImpl implements SlikaMetaDAO, JDBCConfig{


    private JdbcTemplate jdbcTemplateObject;

    public SlikaMetaDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public SlikaMeta addSlika(SlikaMeta slikaMeta) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();
        String sql = "INSERT INTO public.\"Pokojnici_slike_meta\"(fid, naziv, lokacija, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *;";
        SlikaMeta slikaMetaReturn = (SlikaMeta)jdbcTemplateObject.queryForObject(sql, new Object[]{slikaMeta.getNaziv(), slikaMeta.getLokacija(), slikaMeta.getTip(), slikaMeta.getFk()}, slikaMetaMapper);
        return slikaMetaReturn;
    }

    @Override
    public SlikaMeta addSlikaGrob(SlikaMeta slikaMeta) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();
        String sql = "INSERT INTO public.\"Grobovi_slike_meta\"(fid, naziv, lokacija, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *;";
        SlikaMeta slikaMetaReturn = (SlikaMeta)jdbcTemplateObject.queryForObject(sql, new Object[]{slikaMeta.getNaziv(), slikaMeta.getLokacija(), slikaMeta.getTip(), slikaMeta.getFk()}, slikaMetaMapper);
        return slikaMetaReturn;
    }

    @Override
    public Optional<SlikaMeta> getSlikaMetaByFid(Integer fk, String entityTable) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();
        String sql = "SELECT * FROM public.\"" + entityTable +"\" WHERE public.\"" + entityTable + "\".fid = ? ";
        SlikaMeta slikaMeta = (SlikaMeta) jdbcTemplateObject.queryForObject(sql, new Object[]{fk}, slikaMetaMapper);

        Optional<SlikaMeta> oPokojnikSlikaMeta = Optional.ofNullable(slikaMeta);

        return oPokojnikSlikaMeta;
    }

    @Override
    public List<SlikaMeta> getSlikaMetaByPokojnikFid(Integer fid) {

        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();

        String sql = "SELECT * FROM public.\"Pokojnici_slike_meta\" " +
            "INNER JOIN \"Pokojnici\" ON \"Pokojnici_slike_meta\".fk = \"Pokojnici\".fid " +
            "WHERE public.\"Pokojnici\".fid = ? ";

        List<SlikaMeta> slikaMeta = jdbcTemplateObject.query(sql, new Object[]{fid}, slikaMetaMapper);

        return slikaMeta;
    }

    @Override
    public SlikaMeta addSlikaToEntity(SlikaMeta slikaMeta, String entityTable) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();

        String sql = new StringBuilder().append("INSERT INTO public.\"").append(entityTable).append("\"(fid, naziv, lokacija, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *;").toString();
        SlikaMeta slikaMetaReturn = (SlikaMeta)jdbcTemplateObject.queryForObject(sql,
            new Object[]{slikaMeta.getNaziv(),
                slikaMeta.getLokacija(),
                slikaMeta.getTip(),
                slikaMeta.getFk()},
            slikaMetaMapper);
        return slikaMetaReturn;
    }
}
