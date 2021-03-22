package it.geosolutions.mapstore.DAO.PokojnikSlika;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.PokojnikSlika;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PokojnikSlikaDAOImpl implements PokojnikSlikaDAO{


    private JdbcTemplate jdbcTemplateObject;

    public PokojnikSlikaDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public String addSlika(PokojnikSlika pokojnikSlika) {
        String sql = "INSERT INTO public.\"Pokojnici_slike\"(fid, naziv, slika, tip, fk) VALUES (DEFAULT, ?, ?, ?, ?);";
        Integer numberOfAffectedRows = jdbcTemplateObject.update(sql, pokojnikSlika.getNaziv(), pokojnikSlika.getSlika(), pokojnikSlika.getTip(), pokojnikSlika.getFk());
        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";
        return json;
    }

    @Override
    public PokojnikSlika getSlikaByFid(Integer fid) {
        PokojnikSlikaMapper pokojnikSlikaMapper = new PokojnikSlikaMapper();
        String sql = "SELECT * FROM public.\"Pokojnici_slike\" WHERE public.\"Pokojnici_slike\".fid = ? ";
        PokojnikSlika pokojnikSlika = (PokojnikSlika) jdbcTemplateObject.queryForObject(sql, new Object[]{fid}, pokojnikSlikaMapper);
        return pokojnikSlika;
    }
}
