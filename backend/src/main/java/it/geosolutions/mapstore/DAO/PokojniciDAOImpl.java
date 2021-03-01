package it.geosolutions.mapstore.DAO;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
    public List<Pokojnik> listPage(Integer pageNum) {
        Integer offset, limit;
        limit = 10;
        offset = (pageNum-1) * limit;
        String sql = "SELECT * FROM public.\"Pokojnici\" LIMIT ? OFFSET ?";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, new Object[]{limit, offset} ,pokojnikMapper);
        return pokojnici;
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
