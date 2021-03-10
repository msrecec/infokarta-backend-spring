package it.geosolutions.mapstore.DAO.Pokojnici;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PokojniciDAOImpl implements PokojniciDAO, JDBCConfig {

    private JdbcTemplate jdbcTemplateObject;

    public PokojniciDAOImpl() {
        this.jdbcTemplateObject = new JdbcTemplate(JDBCConfig.postgresqlDataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> listColumns() {
        DataSource dataSource = JDBCConfig.postgresqlDataSource();
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
    public String listPokojnici() {
        String sql = "SELECT * FROM public.\"Pokojnici\" ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, pokojnikMapper);
        return JSONUtils.fromListToJSON(pokojnici);
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
    public String searchPokojnici(Optional<String> oIme,
                                          Optional<String> oPrezime,
                                          Optional<String> oPocGodinaUkopa,
                                          Optional<String> oKonGodinaUkopa,
                                          Optional<String> oGroblje,
                                          Optional<Integer> oPage) {
        // input params
        String ime = "", prezime = "", pocGodinaUkopa = "", konGodinaUkopa = "", groblje = "";
        Integer page = null, offset = null, limit = null;
        //mapper and util properties
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List<Pokojnik> pokojnici;
        ArrayList<Object> objArrList = new ArrayList<>();
        boolean and = false;
        Integer countInt = null;
        String json = "";

        //sql strings
        String select = "SELECT * ";
        String selectCount = "SELECT COUNT(*) ";

        String grobljeSQL = "INNER JOIN public.\"Grobovi\" ON \"Pokojnici\".fk = public.\"Grobovi\".fid WHERE public.\"Grobovi\".\"Groblje\" ILIKE ? ";
        String imeSQL = "\"IME\" ILIKE ? ";
        String prezimeSQL = "\"PREZIME\" ILIKE ? ";
        String godineSQL = "TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ";
        String orderBySQL = "ORDER BY \"Pokojnici\".fid ";
        String limitAndOffsetSQL = "LIMIT ? OFFSET ? ";

        String sql = "FROM public.\"Pokojnici\" ";

        if(oGroblje.isPresent()) {
            groblje = oGroblje.get().trim();
            sql+=grobljeSQL;
            and = true;
            objArrList.add(groblje);
        }

        if(oIme.isPresent()) {
            ime = oIme.get().trim();
            ime = "%"+ime+"%";
            if(and) {
                sql+="AND ";
            } else {
                and = true;
                sql+="WHERE ";
            }
            sql+=imeSQL;
            objArrList.add(ime);
        }
        if(oPrezime.isPresent()) {
            prezime = oPrezime.get().trim();
            prezime = "%"+prezime+"%";
            if(and) {
                sql+="AND ";
            } else {
                and = true;
                sql+="WHERE ";
            }
            sql+=prezimeSQL;
            objArrList.add(prezime);
        }
        if(oPocGodinaUkopa.isPresent()&&oKonGodinaUkopa.isPresent()) {
            pocGodinaUkopa = oPocGodinaUkopa.get().trim();
            konGodinaUkopa = oKonGodinaUkopa.get().trim();
            if(and) {
                sql+="AND ";
            } else {
                and = true;
                sql+="WHERE ";
            }
            sql+=godineSQL;
            objArrList.add(pocGodinaUkopa);
            objArrList.add(konGodinaUkopa);
        }

        if(oPage.isPresent()) {
            page = oPage.get();
            if(page<=0) {
                page = 1;
            }
            limit = 30;
            offset = (page-1) * limit;

            countInt = jdbcTemplateObject.queryForInt(selectCount+sql, objArrList.toArray());

            sql+=orderBySQL;
            sql+=limitAndOffsetSQL;
            objArrList.add(limit);
            objArrList.add(offset);

            pokojnici = jdbcTemplateObject.query(select+sql, objArrList.toArray(), pokojnikMapper);

            json = JSONUtils.fromListToJSON(pokojnici);

            json = "{\"pokojnici\":" + json + ",\"totalSearchMatchCount\":" + "\"" + countInt + "\"}";

            return json;

        } else {

            countInt = jdbcTemplateObject.queryForInt(selectCount+sql, objArrList.toArray());

            sql+=orderBySQL;

            pokojnici = jdbcTemplateObject.query(select+sql, objArrList.toArray(), pokojnikMapper);

            json = JSONUtils.fromListToJSON(pokojnici);

            json = "{\"pokojnici\":" + json + ",\"totalSearchMatchCount\":" + "\"" + countInt + "\"}";

            return json;
        }

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
