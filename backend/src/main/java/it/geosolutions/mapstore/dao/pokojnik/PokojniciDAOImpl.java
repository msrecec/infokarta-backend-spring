package it.geosolutions.mapstore.dao.pokojnik;

import it.geosolutions.mapstore.config.jdbc.JdbcConfig;
import it.geosolutions.mapstore.config.jdbc.JdbcConfigImpl;
import it.geosolutions.mapstore.dao.DAO;
import it.geosolutions.mapstore.dto.pokojnik.PokojnikDTO;
import it.geosolutions.mapstore.model.pokojnik.Pokojnik;
import it.geosolutions.mapstore.utils.EncodingUtils;
import it.geosolutions.mapstore.utils.JSONUtils;
import it.geosolutions.mapstore.utils.search.SearchEntity;
import it.geosolutions.mapstore.utils.search.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PokojniciDAOImpl implements PokojniciDAO {

    @Autowired
    JdbcConfigImpl jdbcConfig;
    @Autowired
    @Qualifier("jdbcTemplateObjectFactory")
    private JdbcTemplate jdbcTemplateObject;
    @Autowired
    SearchUtils<Pokojnik> search;

    public PokojniciDAOImpl() {

    }

    @Override
    public Optional<Pokojnik> findById(Integer id) {
        String sql = "SELECT * FROM \"pokojnici\" WHERE fid = ? ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        Optional<Pokojnik> pokojnik;

        try {
            pokojnik = Optional.ofNullable((Pokojnik)jdbcTemplateObject.queryForObject(sql,
                new Object[]{id}, pokojnikMapper));
        } catch (EmptyResultDataAccessException e) {
            pokojnik = Optional.empty();
        }

        return pokojnik;
    }

    @Override
    public List<Pokojnik> findAll() {
        String sql = "SELECT * FROM \"pokojnici\" ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List<Pokojnik> pokojnik;

        try {
            pokojnik =  jdbcTemplateObject.query(sql, pokojnikMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            pokojnik = new ArrayList();
        }

        return pokojnik;
    }

    @Override
    public Integer findCount() {
        String sql = "SELECT COUNT(*) FROM \"pokojnici\"";
        Integer count;

        try {
            count =  jdbcTemplateObject.queryForInt(sql);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            count = 0;
        }

        return count;
    }

    @Override
    public List<Pokojnik> findPaginated(Integer page) {
        Integer limit = DAO.pageSize;

        Integer offset = (page-1) * limit;

        String sql = "SELECT * FROM public.\"pokojnici\" ORDER BY fid LIMIT ? OFFSET ? ";

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        List <Pokojnik> pokojnici;

        try {
            pokojnici = jdbcTemplateObject.query(sql, new Object[] {limit, offset}, pokojnikMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            pokojnici = new ArrayList<>();
        }

        return pokojnici;
    }

    @Override
    public Optional<Pokojnik> save(Pokojnik pokojnik) {
        String sql = "INSERT INTO public.\"pokojnici\"(\n" +
            "\tfid, fk, \"IME I PREZIME\", \"Prezime djevoja??ko\", \"IME OCA\", \"NADIMAK\", \"OIB\", \"SPOL\", \"DATU RO??ENJA\", \"Bra??no stanje\", " +
            "\"MJESTO STANOVANJA\", \"ADRESA STANOVANJA\", \"Ime i prezime bra??nog druga\", \"DOB\", \"UZROK SMRTI\", \"Mjesto smrti\", \"DATUM SMRTI\", " +
            "\"DATUM KREMIRANJA\", \"DATUM UKOPA\", \"oznaka grobnice\", groblje, \"Naknadni upisi i bilje??ke\", \"Godina ukopa\", \"USLUGA\", \"RA??UN\", " +
            "\"DATUM USLUGE\", \"IME\", \"PREZIME\")\n" +
            "\tVALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Optional<Pokojnik> oPokojnik;

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        Object[] params = new Object[] {
            pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getFid()
        };

        try {
            oPokojnik = Optional.ofNullable((Pokojnik)jdbcTemplateObject.queryForObject(sql, params, pokojnikMapper));
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            oPokojnik = Optional.empty();
        }

        return oPokojnik;
    }

    @Override
    public Optional<Pokojnik> update(Pokojnik pokojnik) {
        String sql = "UPDATE \"pokojnici\" SET \"IME I PREZIME\" = ?, \"Prezime djevoja??ko\" = ?, \"IME OCA\" = ?, \"NADIMAK\" = ?, \"OIB\" = ?, \"SPOL\" = ?, \"DATU RO??ENJA\" = ?, \n" +
            "\"Bra??no stanje\" = ?, \"MJESTO STANOVANJA\" = ?, \"ADRESA STANOVANJA\" = ?, \"Ime i prezime bra??nog druga\" = ?, \"DOB\" = ?, \"UZROK SMRTI\" = ?,\n" +
            "\"Mjesto smrti\" = ?, \"DATUM SMRTI\" = ?, \"DATUM KREMIRANJA\" = ?, \"DATUM UKOPA\" = ?, \"oznaka grobnice\" = ?, \"groblje\" = ?, \"Naknadni upisi i bilje??ke\" = ?,\n" +
            "\"Godina ukopa\" = ?, \"USLUGA\" = ?, \"RA??UN\" = ?, \"DATUM USLUGE\" = ?, \"IME\" = ?, \"PREZIME\" = ? WHERE fid = ?";

        Optional<Pokojnik> oPokojnik;
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        Object[] params = new Object[] {
            pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getFid()
        };

        try {
            oPokojnik = Optional.ofNullable((Pokojnik)jdbcTemplateObject.queryForObject(sql, params, pokojnikMapper));
        } catch(EmptyResultDataAccessException e) {

        }

        return Optional.empty();
    }

    @Override
    public List<String> listColumns() {
        DataSource dataSource = jdbcConfig.getDataSource();
        Connection conn = null;
        Statement stmt;
        ArrayList<String> columnNames = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"pokojnici\" LIMIT 1");

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
        String sql = "SELECT * FROM public.\"pokojnici\" LIMIT 1";

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        return (Pokojnik)jdbcTemplateObject.queryForObject(sql, pokojnikMapper);
    }

    @Override
    public String listPokojnici() {
        String sql = "SELECT * FROM public.\"pokojnici\" ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        List <Pokojnik> pokojnici = jdbcTemplateObject.query(sql, pokojnikMapper);
        return JSONUtils.fromListToJSON(pokojnici);
    }

    @Override
    public Pokojnik getPokojnikById(Optional<Integer> oId) {
        Integer id = oId.get();
        String sql = "SELECT * FROM \"pokojnici\" WHERE fid = ? ORDER BY fid";
        PokojnikMapper pokojnikMapper = new PokojnikMapper();
        Pokojnik pokojnik = (Pokojnik) jdbcTemplateObject.queryForObject(sql, new Object[]{id}, pokojnikMapper);
        return pokojnik;
    }

    @Override
    public List<Pokojnik> getPokojnikByGrobljeFid(Integer grobljeFid) {

        List<Pokojnik> pokojnici;

        String sql = "SELECT * FROM \"pokojnici\" INNER JOIN \"grobovi\" ON \"pokojnici\".fk = \"grobovi\".fid " +
            "WHERE \"grobovi\".fid = ?";

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        try{
            pokojnici = jdbcTemplateObject.query(sql, new Object[]{grobljeFid}, pokojnikMapper);
        } catch(EmptyResultDataAccessException e) {
            pokojnici = new ArrayList();
        }
        return pokojnici;
    }

    @Override
    public List<Pokojnik> fullSearch(Map<String, Object> params, SearchEntity entity,
                                             Integer page, List<SearchEntity> orderedEntities) {

        PokojnikMapper pokojnikMapper = new PokojnikMapper();

        List<Pokojnik> pokojnici = search.fullSearchList(params, entity, page, DAO.pageSize, pokojnikMapper, orderedEntities);

        return pokojnici;
    }

    @Override
    public Integer fullSearchCount(Map<String, Object> params, SearchEntity entity, List<SearchEntity> orderedEntities) {
        return search.fullSearchListCount(params, entity, orderedEntities);
    }

    /**
     * Pokojnici dynamic search
     * WARNING: NOT CLEAN -- NEEDS REFACTOR !!!
     * WARNING: CHANGE AT YOUR OWN RISK !!!
     *
     * @param oIme name of deceased
     * @param oPrezime surname of deceased
     * @param oPocGodinaUkopa start year of deceased entities that area being searched
     * @param oKonGodinaUkopa end year of deceased entities that area being searched -- start and end refer to the
     *                        year of death
     * @param oGroblje graveyard
     * @param oPage pagination (page number)
     * @return json string
     * @throws UnsupportedEncodingException
     */

    @Override
    public String searchPokojnici(
        Optional<String> oIme,
        Optional<String> oPrezime,
        Optional<String> oPocGodinaUkopa,
        Optional<String> oKonGodinaUkopa,
        Optional<String> oGroblje,
        Optional<Integer> oPage
    ) throws UnsupportedEncodingException {
        // input params
        String ime = "", prezime = "", pocGodinaUkopa = "", konGodinaUkopa = "", groblje = "";
        Integer page = null, offset = null, limit = null, grob = null;
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

        String grobljeSQL = "INNER JOIN public.\"grobovi\" ON \"pokojnici\".fk = public.\"grobovi\".fid INNER JOIN public.\"groblja\" ON " +
            "public.\"grobovi\".fk = public.\"groblja\".fid WHERE public.\"groblja\".\"naziv\" ILIKE ? ";
        String grobSQL = "INNER JOIN public.\"grobovi\" ON \"pokojnici\".fk = public.\"grobovi\".fid WHERE public.\"grobovi\".fid = ? ";
        String imeSQL = "\"IME\" ILIKE ? ";
        String prezimeSQL = "\"PREZIME\" ILIKE ? ";
        String godineSQL = "TRIM(\"Godina ukopa\") >= ? AND TRIM(\"Godina ukopa\") <= ? ";
        String orderBySQL = "ORDER BY \"pokojnici\".fid ";
        String limitAndOffsetSQL = "LIMIT ? OFFSET ? ";

        String sql = "FROM public.\"pokojnici\" ";

//        if(oGrobFid.isPresent()) {
//            grob = oGrobFid.get();
//            sql+=grobSQL;
//            and = true;
//            objArrList.add(grob);
//        }

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
        String sql = "SELECT COUNT(*) FROM public.\"pokojnici\"";
        Integer count = jdbcTemplateObject.queryForInt(sql);
        return count;
    }

    @Override
    public String updatePokojnik(Pokojnik pokojnik) {
        String sql = "UPDATE \"pokojnici\" SET \"IME I PREZIME\" = ?, \"Prezime djevoja??ko\" = ?, \"IME OCA\" = ?, \"NADIMAK\" = ?, \"OIB\" = ?, \"SPOL\" = ?, \"DATU RO??ENJA\" = ?, \n" +
            "\"Bra??no stanje\" = ?, \"MJESTO STANOVANJA\" = ?, \"ADRESA STANOVANJA\" = ?, \"Ime i prezime bra??nog druga\" = ?, \"DOB\" = ?, \"UZROK SMRTI\" = ?,\n" +
            "\"Mjesto smrti\" = ?, \"DATUM SMRTI\" = ?, \"DATUM KREMIRANJA\" = ?, \"DATUM UKOPA\" = ?, \"oznaka grobnice\" = ?, \"groblje\" = ?, \"Naknadni upisi i bilje??ke\" = ?,\n" +
            "\"Godina ukopa\" = ?, \"USLUGA\" = ?, \"RA??UN\" = ?, \"DATUM USLUGE\" = ?, \"IME\" = ?, \"PREZIME\" = ? WHERE fid = ?";

        Integer numberOfAffectedRows = jdbcTemplateObject.update(sql, pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
            pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
            pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
            pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
            pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime(), pokojnik.getFid());

        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";
        return json;
    }

    @Override
    public Integer addPokojnik(Pokojnik pokojnik) {
        String sql = "INSERT INTO public.\"pokojnici\"(\n" +
            "\tfid, fk, \"IME I PREZIME\", \"Prezime djevoja??ko\", \"IME OCA\", \"NADIMAK\", \"OIB\", \"SPOL\", \"DATU RO??ENJA\", \"Bra??no stanje\", " +
            "\"MJESTO STANOVANJA\", \"ADRESA STANOVANJA\", \"Ime i prezime bra??nog druga\", \"DOB\", \"UZROK SMRTI\", \"Mjesto smrti\", \"DATUM SMRTI\", " +
            "\"DATUM KREMIRANJA\", \"DATUM UKOPA\", \"oznaka grobnice\", groblje, \"Naknadni upisi i bilje??ke\", \"Godina ukopa\", \"USLUGA\", \"RA??UN\", " +
            "\"DATUM USLUGE\", \"IME\", \"PREZIME\")\n" +
            "\tVALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Integer numberOfAffectedRows;

        try {

            numberOfAffectedRows = jdbcTemplateObject.update(sql, pokojnik.getFk(),pokojnik.getIme_i_prezime(), pokojnik.getPrezime_djevojacko(), pokojnik.getIme_oca(), pokojnik.getNadimak(), pokojnik.getOib(),
                pokojnik.getSpol(), pokojnik.getDatum_rodjenja(), pokojnik.getBracno_stanje(), pokojnik.getMjesto_stanovanja(), pokojnik.getAdresa_stanovanja(), pokojnik.getIme_i_prezime_bracnog_druga(),
                pokojnik.getDob(), pokojnik.getUzrok_smrti(), pokojnik.getMjesto_smrti(), pokojnik.getDatum_smrti(), pokojnik.getDatum_kremiranja(), pokojnik.getDatum_ukopa(),
                pokojnik.getOznaka_grobnice(), pokojnik.getGroblje(), pokojnik.getNaknadni_upisi_i_biljeske(), pokojnik.getGodina_ukopa(), pokojnik.getUsluga(), pokojnik.getRacun(),
                pokojnik.getDatum_usluge(), pokojnik.getIme(), pokojnik.getPrezime());

        } catch (DataAccessException ex) {
            numberOfAffectedRows = 0;
        }

//        String json = "{\"numberOfAffectedRows\":" + "\"" +numberOfAffectedRows + "\"}";
        return numberOfAffectedRows;
    }

    @Override
    public Integer addPokojnikByGrobljeAndRbr(Pokojnik pokojnik, String groblje, String rbr) {
        String sql = new StringBuilder().append("SELECT \"grobovi\".fid FROM \"grobovi\" INNER JOIN \"groblja\" ON \"grobovi\".fk = \"groblja\".fid ").append("WHERE \"groblja\".naziv ILIKE ? AND \"grobovi\".\"redni_broj\" ILIKE ? ").toString();

        Optional<Integer> oFid = Optional.ofNullable(jdbcTemplateObject.queryForInt(sql, new Object[]{groblje, rbr}));

        if(oFid.isPresent()) {
            pokojnik.setFk(oFid.get());
        }

//        String json = addPokojnik(pokojnik);

        Integer numberOfAffectedRows = addPokojnik(pokojnik);

        return numberOfAffectedRows;
    }
}
