package it.geosolutions.mapstore.dao.slikaMeta.mappers;

import it.geosolutions.mapstore.model.slike.Thumbnail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThumbnailMapper extends SlikaMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {

        Thumbnail thumbnail = new Thumbnail();

        setSlika(thumbnail, rs);

        thumbnail.setLokacija(rs.getString(5));

        return thumbnail;
    }
}
