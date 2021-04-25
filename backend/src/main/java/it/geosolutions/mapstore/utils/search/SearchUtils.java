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
     * TODO - finish this method and implement 'ilike' for strings
     *
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

    public List<T> searchJoinedList (Map<String, Object> params, SearchEntity entity,
                                     Integer page, Integer limit, RowMapper<T> r,
                                     List<SearchEntity> orderedEntities) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select * from ").append(entity.getEntity()).append(" ").toString();


        /**
         * JOIN entities by custom classes
         *
         */
        for(SearchEntity temp : orderedEntities) {
            sql+="inner join "+temp.getEntity()+" on ";
            sql+=temp.getEntity()+"."+temp.getFk()+"="+entity.getEntity()+"."+entity.getFk()+" ";
        }


        /**
         * Search by parameters and remove last 'and' in the string query
         *
         */

        sql +="where ";

        for(String p : params.keySet()) {
            if(p.trim().split(":")[0].equalsIgnoreCase("varchar")) {
                sql += "TRIM("+p.trim().split(":")[1] + ") " + "ilike TRIM(?) and ";
            }
            if(p.trim().split(":")[0].equalsIgnoreCase("int")) {
                sql += p.trim().split(":")[1] + " " + "= ? and ";
            }
            paramList.add(params.get(p));
        }

        sql = sql.trim();

        // 3 is size of 'and' at the end of the string
        sql = sql.substring(0, sql.length() - 3);

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
     * @param page page of elements returned
     * @return sql query
     */

    public List<T> searchList (Map<String, Object> params, String entity, Integer page, Integer limit, RowMapper<T> r) {

        String sql;

        ArrayList<Object> paramList = new ArrayList();

        sql = new StringBuilder().append("select * from ").append(entity).append(" ").toString();

        /**
         * Search by parameters and remove last 'and' in the string query
         *
         */

        sql +="where ";

        for(String p : params.keySet()) {
            if(p.trim().split(":")[0].equalsIgnoreCase("varchar")) {
                sql += "TRIM("+p.trim().split(":")[1] + ") " + "ilike TRIM(?) and ";
            }
            if(p.trim().split(":")[0].equalsIgnoreCase("int")) {
                sql += p.trim().split(":")[1] + " " + "= ? and ";
            }
            paramList.add(params.get(p));
        }

        sql = sql.trim();

        /**
         * 3 is size of 'and' at the end of the string
         *
         */

        sql = sql.substring(0, sql.length() - 3);

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

        System.out.println(sql);

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

        sql = new StringBuilder().append("select COUNT(*) from ").append(entity).append(" ").toString();

        /**
         * Search by parameters and remove last 'and' in the string query
         *
         */

        if(!params.isEmpty()) {

            sql +="where ";

            for(String p : params.keySet()) {
                if(p.trim().split(":")[0].equalsIgnoreCase("varchar")) {
                    sql += "TRIM("+p.trim().split(":")[1] + ") " + "ilike TRIM(?) and ";
                }
                if(p.trim().split(":")[0].equalsIgnoreCase("int")) {
                    sql += p.trim().split(":")[1] + " " + "= ? and ";
                }
                paramList.add(params.get(p));
            }

            sql = sql.trim();

            /**
             * 3 is size of 'and' at the end of the string
             *
             */

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
