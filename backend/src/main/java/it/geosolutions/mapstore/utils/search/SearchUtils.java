package it.geosolutions.mapstore.utils.search;

import it.geosolutions.mapstore.config.jdbc.JdbcConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SearchUtils <T> {
    @Autowired
    @Qualifier("jdbcTemplateObjectFactory")
    private JdbcTemplate jdbcTemplateObject;

    /**
     *
     * Looks for elements with respectable key value pairs for where parameters for entities and returns paginated
     * result
     *
     * Method takes parameters for comparison in sql 'where' clause and respectable operators e.g. like, ilike, =, >, <
     * it also requires entity that is the main entry point of search regardless of join entities
     *
     * page and limit are required for pagination, e.g. page = 2 and limit = 30 for the 31st-60th element in the table
     *
     * orderedEntities represent tables included in join - they are not required, but an instance of empty array
     * is recommended if joins are not being made
     *
     * E.G.
     *
     * For get request in uri
     *
     * <code>
     *     http://localhost:8080/mapstore/rest/config/pokojnici2?ime=ante&prezime=gracin&groblje=Primošten&page=1
     * </code>
     *
     * Following sql query is generated
     *
     * <code>
     *     select * from "pokojnici"
     *     inner join "grobovi" on "pokojnici"."fk"="grobovi"."fid"
     *     inner join "groblja" on "grobovi"."fk"="groblja"."fid"
     *     where TRIM("groblja"."naziv") ilike TRIM(?) and TRIM("pokojnici"."PREZIME") ilike TRIM(?)
     *     and TRIM("pokojnici"."IME") ilike TRIM(?)  order by "pokojnici"."fid"  LIMIT ? OFFSET ?
     * </code>
     *
     * IMPORTANT: orderedEntities must be in specific order for sql join query
     *
     * @param params [sql-type]:[sql-table-column]:[sql-operator] - comparison parameters for where clause as keys
     *               and Objects with required search values as values
     * @param entity Object that represents database entity name, primary and foreign key
     * @param page Page of listed objects
     * @param limit maximum number of objects per page
     * @param r RowMapper for result set iteration
     * @param orderedEntities entities in joins that need to be ordered - e.g. pokojnici, grobovi, groblja -
     * @return list of searched objects
     */

    public List<T> fullSearchList (Map<String, Object> params, SearchEntity entity,
                                     Integer page, Integer limit, RowMapper<T> r,
                                     List<SearchEntity> orderedEntities) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select * from ").append(entity.getEntity()).append(" ").toString();


        sql = joinHandler(entity, orderedEntities, sql);

        sql = whereHandler(params, sql, paramList);

        /**
         * Order by fid
         *
         */

        sql += " order by " + entity.getEntity() + "." + entity.getFid() + " ";

        /**
         * If Count Exists then return total number of found elements
         *
         */

        if(page != null && page > 0 && limit != null && limit > 0 ) {
            sql += " LIMIT ? OFFSET ?";
            paramList.add(limit);
            paramList.add((page-1)*limit);
        }

        List<T> lista;

        try {
            lista = jdbcTemplateObject.query(sql, paramList.toArray(), r);
        } catch (EmptyResultDataAccessException e) {
            lista = new ArrayList<>();
        }

        return lista;
    }


    /**
     * Returns the total number of found rows in the sql query.
     *
     * Required for pagination.
     *
     * Logic is almost identical to fullSearchList method except the fact that this method doesn't include paginated
     * results. Consists of rows determined by where clause comparison by params
     *
     * For more detailed explanation:
     * @see {fullSearchList()}
     *
     * WARNING: orderedEntities must be in specific order for sql join query
     *
     * @param params parameters for where clause in sql query
     * @param entity object of interest in sql query *
     * @param orderedEntities entities in joins that need to be ordered
     * @return total number of rows for query
     *
     */

    public Integer fullSearchListCount (Map<String, Object> params, SearchEntity entity,
                                          List<SearchEntity> orderedEntities) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select count(*) from ").append(entity.getEntity()).append(" ").toString();

        sql = joinHandler(entity, orderedEntities, sql);

        sql = whereHandler(params, sql, paramList);

        Integer count;

        try {
            count = jdbcTemplateObject.queryForInt(sql, paramList.toArray());
        } catch (EmptyResultDataAccessException e) {
            count = 0;
        }

        return count;
    }

    /**
     * Handles join query for entity -> orderedEntities.get(0) -> orderedEntities.get(1) -> etc...
     *
     * if orderedEntities is empty or null then just return sql
     *
     * @param entity
     * @param orderedEntities
     * @param sql
     * @return
     */

    private String joinHandler(SearchEntity entity, List<SearchEntity> orderedEntities, String sql) {
        /**
         * JOIN entities by custom classes
         *
         * if orderedEntities exist
         *
         */

        if (orderedEntities != null && !orderedEntities.isEmpty()) {

            sql += "inner join " + orderedEntities.get(0).getEntity()
                + " on " + entity.getEntity() + "." + entity.getFk()
                + "=" + orderedEntities.get(0).getEntity() + "."
                + orderedEntities.get(0).getFid() + " ";

            if (orderedEntities.size() > 1) {
                for (int i = 0; i < orderedEntities.size() - 1; ++i) {
                    sql += "inner join " + orderedEntities.get(i + 1).getEntity() + " on " +
                        orderedEntities.get(i).getEntity() + "." + orderedEntities.get(i).getFk()
                        + "=" + orderedEntities.get(i + 1).getEntity() + "." + orderedEntities.get(i + 1).getFid() + " ";
                }
            }

        }
        return sql;
    }

    /**
     * Handles conditions provided by Map params and adds required values to paramList for jdbc query
     *
     * @param params
     * @param sql
     * @param paramList
     * @return
     */

    private String whereHandler(Map<String, Object> params, String sql, ArrayList<Object> paramList) {
        /**
         * Search by parameters and remove last 'and' in the string query
         *
         * colon separated
         * split(":")[0] - type
         * split(":")[1] - column
         * split(":")[2] - comparison operator
         *
         */

        sql += "where ";

        for (String p : params.keySet()) {
            if (p.trim().split(":")[0].equalsIgnoreCase("varchar")) {
                sql += "TRIM(" + p.trim().split(":")[1] + ") " + p.trim().split(":")[2] + " TRIM(?) and ";
            }
            if (p.trim().split(":")[0].equalsIgnoreCase("int")) {
                sql += p.trim().split(":")[1] + " " + p.trim().split(":")[2] + " ? and ";
            }
            paramList.add(params.get(p));
        }

        sql = sql.trim();

        /**
         *  3 is size of 'and' at the end of the string
         *
         */

        sql = sql.substring(0, sql.length() - 3);
        return sql;
    }
}
