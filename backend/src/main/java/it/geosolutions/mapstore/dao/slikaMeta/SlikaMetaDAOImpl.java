package it.geosolutions.mapstore.dao.slikaMeta;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.SlikaMeta;
import it.geosolutions.mapstore.utils.EntityUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public SlikaMeta addSlikaGrob(SlikaMeta slikaMeta) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();
        String sql = "INSERT INTO public.\"grobovi_slike_meta\"(fid, naziv, lokacija, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?) RETURNING *;";
        SlikaMeta slikaMetaReturn = (SlikaMeta)jdbcTemplateObject.queryForObject(sql, new Object[]{slikaMeta.getNaziv(), slikaMeta.getLokacija(), slikaMeta.getTip(), slikaMeta.getFk()}, slikaMetaMapper);
        return slikaMetaReturn;
    }

    @Override
    public Optional<SlikaMeta> getSlikaMetaByFid(Integer fk, String entityTable) {
        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();
        String sql = new StringBuilder().append("SELECT * FROM public.\"").append(entityTable).append("\" WHERE public.\"").append(entityTable).append("\".fid = ? ").toString();
        SlikaMeta slikaMeta = (SlikaMeta) jdbcTemplateObject.queryForObject(sql, new Object[]{fk}, slikaMetaMapper);

        Optional<SlikaMeta> oPokojnikSlikaMeta = Optional.ofNullable(slikaMeta);

        return oPokojnikSlikaMeta;
    }

    @Override
    public List<SlikaMeta> getSlikaMetaByEntityFid(Integer fid, String entityTable, String entity) {

        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();

        String sql = new StringBuilder().append("SELECT * FROM public.\"").append(entityTable).append("\" ").append("INNER JOIN public.\"").append(entity).append("\" ON public.\"").append(entityTable).append("\".fk = public.\"").append(entity).append("\".fid ").append("WHERE public.\"").append(entity).append("\".fid = ? ").toString();

        List<SlikaMeta> slikaMeta = jdbcTemplateObject.query(sql, new Object[]{fid}, slikaMetaMapper);

        return slikaMeta;
    }

    @Override
    public SlikaMeta addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity) {

        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();

        if(EntityUtil.isEntity(entity)) {

            String sql = new StringBuilder()
                .append("INSERT INTO public.\"")
                .append(entity.toLowerCase())
                .append("\"(fid, naziv, tip, ")
                .append(entity.toLowerCase())
                .append("_fid) VALUES (DEFAULT, ?, ?, ?) RETURNING *")
                .toString();

            Object[] params = new Object[] {
                slikaMeta.getNaziv(),
                slikaMeta.getTip(),
                slikaMeta.getFk()
            };

            SlikaMeta slikaMetaReturn = (SlikaMeta)jdbcTemplateObject.queryForObject(
                sql,
                params,
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        SlikaMeta slikaMeta = new SlikaMeta();

                        slikaMeta.setFid(rs.getInt(1));
                        slikaMeta.setNaziv(rs.getString(2));
                        slikaMeta.setTip(rs.getString(3));
                        slikaMeta.setFk(rs.getInt(4));

                        return slikaMeta;
                    }
                }
            );

            return slikaMetaReturn;

        } else {

            return null;

        }
    }

    @Override
    public SlikaMeta addSlikaOriginalMetaByEntity(SlikaMeta slikaMeta, String entity) {

        return null;
    }

    @Override
    public SlikaMeta addSlikaThumbnailMetaByEntity(SlikaMeta slikaMeta, String entity) {

        return null;
    }

}
