package application.repository.rowmapper;

import db.RowMapper;
import java.sql.ResultSet;

public class InsertArticleRowMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                throw new RuntimeException("No Article Generated Key found");
            }
            return resultSet.getLong(1);
        } catch (Exception e) {
            throw new RuntimeException("Error inserting article", e);
        }
    }
}
