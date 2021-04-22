package it.geosolutions.mapstore.utils;

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
     * Looks for elements with respectable key value pairs for where parameters for entities and returns paginated
     * result
     *
     * If count = true then return only the total number of elements that are matching parameters
     *
     * @param params input parameters
     * @param entity entity that we are looking through
     * @param page page of elements returned
     * @return sql query
     */

    public List<T> searchList (Map<String, Object> params, String entity, Integer page, Integer limit, RowMapper<T> r) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select * from \"").append(entity).append("\" ").toString();

        /**
         * Search by parameters and remove last 'and' in the string query
         *
         */

        sql +="where ";

        for(String p : params.keySet()) {
            sql += "\"rasvjeta\"."+ "\"" + p.trim() + "\"" + " = ? and ";
            paramList.add(params.get(p));
        }

        sql = sql.trim();

        // 3 is size of 'and' at the end of the string
        sql = sql.substring(0, sql.length() - 3);

        /**
         * If Count Exists then return total number of found elements
         *
         */

        if(page > 0 && limit > 0 ) {
            sql += " LIMIT ? OFFSET ?";
            paramList.add(limit);
            paramList.add((page-1)*limit);
        }

        List<T> lista;

        lista = jdbcTemplateObject.query(sql, paramList.toArray(), r);

        return lista;
    }
    /**
     * Looks for elements with respectable key value pairs for where parameters for entities and returns paginated
     * result
     *
     * If count = true then return only the total number of elements that are matching parameters
     *
     * @param params input parameters
     * @param entity entity that we are looking through
     * @return sql query
     */

    public Integer searchCount (Map<String, Object> params, String entity) {

        String sql;

        List<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select COUNT(*) from \"").append(entity).append("\" ").toString();

        /**
         * Search by parameters and remove last 'and' in the string query
         *
         */

        if(!params.isEmpty()) {

            sql +="where ";

            for(String p : params.keySet()) {
                sql += "\"rasvjeta\"."+ "\"" + p.trim() + "\"" + " = ? and ";
                paramList.add(params.get(p));
            }

            sql = sql.trim();

            // 3 is size of 'and' at the end of the string
            sql = sql.substring(0, sql.length() - 3);

        }

        Integer count;

        try {
            count = jdbcTemplateObject.queryForInt(sql, paramList.toArray());
        } catch (EmptyResultDataAccessException e) {
            count = 0;
        }

        return count;
    }


}
