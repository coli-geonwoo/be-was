package application.repository.rowmapper;

import db.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountArticleRowMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs) {
        try {
            return rs.getInt(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
