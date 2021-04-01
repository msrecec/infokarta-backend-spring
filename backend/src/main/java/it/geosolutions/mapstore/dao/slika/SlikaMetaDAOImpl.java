package it.geosolutions.mapstore.dao.slika;

import it.geosolutions.mapstore.config.FileSystemConfig;
import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.model.SlikaMeta;
import it.geosolutions.mapstore.utils.EntityUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
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

        String sql = new StringBuilder()
            .append("SELECT * FROM public.\"").append(entityTable).append("\" ")
            .append("INNER JOIN public.\"").append(entity).append("\" ON public.\"").append(entityTable)
            .append("\".fk = public.\"").append(entity).append("\".fid ").append("WHERE public.\"")
            .append(entity).append("\".fid = ? ").toString();

        List<SlikaMeta> slikaMeta = jdbcTemplateObject.query(sql, new Object[]{fid}, slikaMetaMapper);

        return slikaMeta;
    }

    @Override
    public SlikaMeta addSlikaMetaByEntity(SlikaMeta slikaMeta, String entity) {

        SlikaMetaMapper slikaMetaMapper = new SlikaMetaMapper();

        if(EntityUtil.isEntity(entity)) {

            String sql = new StringBuilder()
                .append("INSERT INTO public.\"slike_meta\"")
                .append("(fid, naziv, tip, original, thumbnail, ")
                .append(entity.toLowerCase())
                .append("_fid) VALUES (DEFAULT, ?, ?, ?, ?, ?)")
                .append(" RETURNING fid, naziv, tip, original, thumbnail, ")
                .append(entity.toLowerCase())
                .append("_fid")
                .toString();

            Object[] params = new Object[] {
                slikaMeta.getNaziv(),
                slikaMeta.getTip(),
                slikaMeta.getOriginal(),
                slikaMeta.getThumbnail(),
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
                        slikaMeta.setOriginal(rs.getString(4));
                        slikaMeta.setThumbnail(rs.getString(5));
                        slikaMeta.setFk(rs.getInt(6));

                        return slikaMeta;
                    }
                }
            );

            return slikaMetaReturn;

        } else {

            return null;

        }
    }

}
