package it.geosolutions.mapstore.DAO.Pokojnik;

import it.geosolutions.mapstore.config.JDBCConfig;
import it.geosolutions.mapstore.pojo.Pokojnik;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
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
    public Pokojnik getFirstPokojnik() {
        String sql = "SELECT * FROM public.\"Pokojnici\" LIMIT 1";

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        return (Pokojnik)jdbcTemplateObject.queryForObject(sql, pokojnikMapper);
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
        String sql = "SELECT * FROM \"Pokojnici\" WHERE fid = ? ORDER BY fid";
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
                                          Optional<Integer> oPage) throws UnsupportedEncodingException {
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

        String grobljeSQL = "INNER JOIN public.\"Grobovi\" ON \"Pokojnici\".fk = public.\"Grobovi\".fid INNER JOIN public.\"Groblja\" ON " +
            "public.\"Grobovi\".fk = public.\"Groblja\".fid WHERE public.\"Groblja\".\"naziv\" ILIKE ? ";
        String imeSQL = "\"IME\" ILIKE ? ";
        String prezimeSQL = "\"PREZIME\" ILIKE ? ";
        String godineSQL = "TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ";
        String orderBySQL = "ORDER BY \"Pokojnici\".fid ";
        String limitAndOffsetSQL = "LIMIT ? OFFSET ? ";

        String sql = "FROM public.\"Pokojnici\" ";

        if(oGroblje.isPresent()) {
            groblje = EncodingUtils.decodeISO88591(oGroblje.get()).trim();
            sql+=grobljeSQL;
            and = true;
            objArrList.add(groblje);
        }

        if(oIme.isPresent()) {
            ime = EncodingUtils.decodeISO88591(oIme.get()).trim();
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
            prezime = EncodingUtils.decodeISO88591(oPrezime.get()).trim();
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
    public String updatePokojnik(Pokojnik pokojnik) {
        String sql = "UPDATE \"Pokojnici\" SET \"IME I PREZIME\" = ?, \"Prezime djevojačko\" = ?, \"IME OCA\" = ?, \"NADIMAK\" = ?, \"OIB\" = ?, \"SPOL\" = ?, \"DATU ROĐENJA\" = ?, \n" +
            "\"Bračno stanje\" = ?, \"MJESTO STANOVANJA\" = ?, \"ADRESA STANOVANJA\" = ?, \"Ime i prezime bračnog druga\" = ?, \"DOB\" = ?, \"UZROK SMRTI\" = ?,\n" +
            "\"Mjesto smrti\" = ?, \"DATUM SMRTI\" = ?, \"DATUM KREMIRANJA\" = ?, \"DATUM UKOPA\" = ?, \"oznaka grobnice\" = ?, \"groblje\" = ?, \"Naknadni upisi i bilješke\" = ?,\n" +
            "\"Godina ukopa\" = ?, \"USLUGA\" = ?, \"RAČUN\" = ?, \"DATUM USLUGE\" = ?, \"IME\" = ?, \"PREZIME\" = ? WHERE fid = ?";

        Integer numberOfAffectedRows = jdbcTemplateObject.update(sql, pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getFid());

        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";
        return json;
    }

    @Override
    public String addPokojnik(Pokojnik pokojnik) {
        String sql = "INSERT INTO public.\"Pokojnici\"(\n" +
            "\tfid, fk, \"IME I PREZIME\", \"Prezime djevojačko\", \"IME OCA\", \"NADIMAK\", \"OIB\", \"SPOL\", \"DATU ROĐENJA\", \"Bračno stanje\", " +
            "\"MJESTO STANOVANJA\", \"ADRESA STANOVANJA\", \"Ime i prezime bračnog druga\", \"DOB\", \"UZROK SMRTI\", \"Mjesto smrti\", \"DATUM SMRTI\", " +
            "\"DATUM KREMIRANJA\", \"DATUM UKOPA\", \"oznaka grobnice\", groblje, \"Naknadni upisi i bilješke\", \"Godina ukopa\", \"USLUGA\", \"RAČUN\", " +
            "\"DATUM USLUGE\", \"IME\", \"PREZIME\")\n" +
            "\tVALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Integer numberOfAffectedRows = jdbcTemplateObject.update(sql, pokojnik.getFk(),pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime());

        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";
        return json;
    }

    @Override
    public String addPokojnikByGrobljeAndRbr(Pokojnik pokojnik, String groblje, String rbr) {
        String sql = "SELECT \"Grobovi\".fid FROM \"Grobovi\" INNER JOIN \"Groblja\" ON \"Grobovi\".fk = \"Groblja\".fid " +
            "WHERE \"Groblja\".naziv ILIKE ? AND \"Grobovi\".\"Rednibroj\" ILIKE ? ";

        Optional<Integer> oFid = Optional.ofNullable(jdbcTemplateObject.queryForInt(sql, new Object[]{groblje, rbr}));

        if(oFid.isPresent()) {
            pokojnik.setFk(oFid.get());
        }

        String json = addPokojnik(pokojnik);

        return json;
    }
}
