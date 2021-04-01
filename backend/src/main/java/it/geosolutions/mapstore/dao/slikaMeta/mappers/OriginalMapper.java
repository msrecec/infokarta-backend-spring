package it.geosolutions.mapstore.dao.slikaMeta.mappers;

import it.geosolutions.mapstore.model.slike.Original;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OriginalMapper extends SlikaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        Original original = new Original();

        setSlika(original, rs);

        original.setLokacija(rs.getString(5));

        return original;
    }
}
