package application.repository.rowmapper;

import db.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountArticleRowMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs) {
        try {
            if(!rs.next()) {
                throw new RuntimeException("No Count Article Found");
            }
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
