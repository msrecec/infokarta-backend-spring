package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<String> listColumns() {
        JDBCConfig jdbcConfig = new JDBCConfig();
        DataSource dataSource = jdbcConfig.postgresqlDataSource();
        Connection conn = null;
        Statement stmt;
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"Pokojnici\" LIMIT 1");

            if(rs != null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i = 0; i < rsmd.getColumnCount(); ++i) {
                    columnNames.add(rsmd.getColumnName(i+1));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return columnNames;
    }

    @Override
    public List<Pokojnik> listPokojnici() {
        String sql = "SELECT * FROM public.\"Pokojnici\" ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, pokojnikMapper);
        return pokojnici;
    }

    @Override
    public Pokojnik getPokojnikById(Optional<Integer> oId) {
        Integer id = oId.get();
        String sql = "SELECT * FROM public.\"Pokojnici\" WHERE fid = ? ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        Pokojnik pokojnik = (Pokojnik) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, pokojnikMapper);
        return pokojnik;
    }

    @Override
    public List<Pokojnik> getPokojnikByImeOrPrezimeOrPage(Optional<String> oIme,
                                                          Optional<String> oPrezime,
                                                          Optional<String> oPocGodinaUkopa,
                                                          Optional<String> oKonGodinaUkopa,
                                                          Optional<Integer> oPage) {
        String ime = "", prezime = "", pocGodinaUkopa = "", konGodinaUkopa = "";
        Integer page = null, offset = null, limit = null;
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List<Pokojnik> pokojnici;

        if(oIme.isPresent()) {
            ime = oIme.get().trim();
            ime = "%"+ime+"%";
        }
        if(oPrezime.isPresent()) {
            prezime = oPrezime.get().trim();
            prezime = "%"+prezime+"%";
        }
        if(oPocGodinaUkopa.isPresent()&&oKonGodinaUkopa.isPresent()) {
            pocGodinaUkopa = oPocGodinaUkopa.get().trim();
            konGodinaUkopa = oKonGodinaUkopa.get().trim();
        }
        if(oPage.isPresent()) {
            page = oPage.get();
            if(page<=0) {
                page = 1;
            }
            limit = 30;
            offset = (page-1) * limit;
        }

        if(!oPocGodinaUkopa.isPresent()&&!oKonGodinaUkopa.isPresent()) {
            if(!oPage.isPresent()) {
                if(!oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime}, pokojnikMapper);
                }
                else if(oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime}, pokojnikMapper);
                } else if(!oIme.isPresent() && oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"PREZIME\" ILIKE ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{prezime}, pokojnikMapper);
                } else {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? AND \"PREZIME\" ILIKE ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, prezime}, pokojnikMapper);
                }
            } else {
                if(!oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT * FROM public.\"Pokojnici\" ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{limit, offset} ,pokojnikMapper);
                }
                else if(oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, limit, offset}, pokojnikMapper);
                } else if(!oIme.isPresent() && oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"PREZIME\" ILIKE ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{prezime, limit, offset}, pokojnikMapper);
                } else {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? AND \"PREZIME\" ILIKE ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, prezime, limit, offset}, pokojnikMapper);
                }
            }
        } else {
            if(!oPage.isPresent()) {
                if(!oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT * FROM public.\"Pokojnici\" WHERE TRIM(\"Godina ukopa\") >= ? " +
                        "AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{pocGodinaUkopa, konGodinaUkopa} ,pokojnikMapper);
                }
                else if(oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? " +
                        "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, pocGodinaUkopa, konGodinaUkopa}, pokojnikMapper);
                } else if(!oIme.isPresent() && oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"PREZIME\" ILIKE ? " +
                        "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{prezime, pocGodinaUkopa, konGodinaUkopa}, pokojnikMapper);
                } else {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? AND \"PREZIME\" ILIKE ? " +
                        "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, prezime, pocGodinaUkopa, konGodinaUkopa}, pokojnikMapper);
                }
            } else {
                if(!oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT * FROM public.\"Pokojnici\" WHERE TRIM(\"Godina ukopa\") >= ? " +
                        "AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{pocGodinaUkopa, konGodinaUkopa, limit, offset} ,pokojnikMapper);
                }
                else if(oIme.isPresent() && !oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? " +
                        "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, pocGodinaUkopa, konGodinaUkopa, limit, offset}, pokojnikMapper);
                } else if(!oIme.isPresent() && oPrezime.isPresent()) {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"PREZIME\" ILIKE ? AND " +
                        "TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{prezime, pocGodinaUkopa, konGodinaUkopa, limit, offset}, pokojnikMapper);
                } else {
                    String sql = "SELECT DISTINCT * FROM public.\"Pokojnici\" WHERE \"IME\" ILIKE ? AND \"PREZIME\" ILIKE ? " +
                        "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ORDER BY fid LIMIT ? OFFSET ?";
                    pokojnici = jdbcTemplateObject.query(sql, new Object[]{ime, prezime, pocGodinaUkopa, konGodinaUkopa, limit, offset}, pokojnikMapper);
                }
            }
        }


        return pokojnici;
    }

    @Override
    public Integer getPokojnikCount() {
        String sql = "SELECT COUNT(*) FROM public.\"Pokojnici\"";
        Integer count = jdbcTemplateObject.queryForInt(sql);
        return count;
    }

    @Override
    public boolean addPokojnik(Pokojnik pokojnik) {
        String sql = "";
        return false;
    }
}
