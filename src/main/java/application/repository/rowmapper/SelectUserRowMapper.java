package application.repository.rowmapper;

import application.model.User;
import db.RowMapper;
import java.sql.ResultSet;

public class SelectUserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs) {
        try {
            if(!rs.next()) {
                throw new RuntimeException("No User Row Found");
            }
            String userId = rs.getString("user_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            return new User(userId, password, name, email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map user row", e);
        }
    }
}
