package it.geosolutions.mapstore.DAO.Pokojnici;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
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
    public List<Pokojnik> searchPokojnici(Optional<String> oIme,
                                          Optional<String> oPrezime,
                                          Optional<String> oPocGodinaUkopa,
                                          Optional<String> oKonGodinaUkopa,
                                          Optional<String> oGroblje,
                                          Optional<Integer> oPage) {
        String ime = "", prezime = "", pocGodinaUkopa = "", konGodinaUkopa = "", groblje = "";
        Integer page = null, offset = null, limit = null;
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List<Pokojnik> pokojnici;
        Object objArr[];
        ArrayList<Object> objArrList = new ArrayList<>();
        boolean and = false;

        String sql = "SELECT * FROM public.\"Pokojnici\" ";

        if(oGroblje.isPresent()) {
            groblje = oGroblje.get().trim();
            sql+="INNER JOIN public.\"Grobovi\" ON \"Pokojnici\".fk = public.\"Grobovi\".fid WHERE public.\"Grobovi\".\"Groblje\" ILIKE ? ";
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
            sql+="\"IME\" ILIKE ? ";
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
            sql+="\"PREZIME\" ILIKE ? ";
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
            sql+="TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ";
            objArrList.add(pocGodinaUkopa);
            objArrList.add(konGodinaUkopa);
        }

        sql+="ORDER BY \"Pokojnici\".fid ";

        if(oPage.isPresent()) {
            page = oPage.get();
            if(page<=0) {
                page = 1;
            }
            limit = 30;
            offset = (page-1) * limit;
            sql+="LIMIT ? OFFSET ?";
            objArrList.add(limit);
            objArrList.add(offset);
        }

        String sql2 = "SELECT * FROM public.\"Pokojnici\" INNER JOIN public.\"Grobovi\" ON \"Pokojnici\".fk = public.\"Grobovi\".fid " +
            "WHERE \"IME\" ILIKE ? AND \"PREZIME\" ILIKE ? " +
            "AND TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? AND public.\"Grobovi\".\"Groblje\" ILIKE ? " +
            "ORDER BY \"Pokojnici\".fid LIMIT ? OFFSET ?";


        objArr = objArrList.toArray();

        pokojnici = jdbcTemplateObject.query(sql, objArr, pokojnikMapper);

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
