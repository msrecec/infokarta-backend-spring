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
     * If count = true then return only the total number of elements that are matching parameters
     *
     * WARNING: orderedEntities must be in specific order for sql join query
     *
     * @param params
     * @param entity
     * @param page
     * @param limit
     * @param r
     * @param orderedEntities entities in joins that need to be ordered
     * @return
     */

    public List<T> fullSearchList (Map<String, Object> params, SearchEntity entity,
                                     Integer page, Integer limit, RowMapper<T> r,
                                     List<SearchEntity> orderedEntities) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select * from ").append(entity.getEntity()).append(" ").toString();


        sql = joinHandler(entity, orderedEntities, sql, orderedEntities != null && !orderedEntities.isEmpty());

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
     *
     * Looks for number of elements with respectable key value pairs for where parameters for entities
     *
     * If count = true then return only the total number of elements that are matching parameters
     *
     * WARNING: orderedEntities must be in specific order for sql join query
     *
     * @param params
     * @param entity
     * @param orderedEntities entities in joins that need to be ordered
     * @return
     */

    public Integer fullSearchListCount (Map<String, Object> params, SearchEntity entity,
                                          List<SearchEntity> orderedEntities) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select count(*) from ").append(entity.getEntity()).append(" ").toString();

        sql = joinHandler(entity, orderedEntities, sql, !orderedEntities.isEmpty());

        sql = whereHandler(params, sql, paramList);

        Integer count;

        try {
            count = jdbcTemplateObject.queryForInt(sql, paramList.toArray());
        } catch (EmptyResultDataAccessException e) {
            count = 0;
        }

        return count;
    }


    private String joinHandler(SearchEntity entity, List<SearchEntity> orderedEntities, String sql, boolean b) {
        /**
         * JOIN entities by custom classes
         *
         * if orderedEntities exist
         *
         */

        if (b) {

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
