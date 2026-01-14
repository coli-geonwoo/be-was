package application.repository.rowmapper;

import application.model.User;
import db.RowMapper;
import java.sql.ResultSet;
import java.util.Optional;

public class SelectUserRowMapper implements RowMapper<Optional<User>> {

    @Override
    public Optional<User> mapRow(ResultSet rs) {
        try {
            if(!rs.next()) {
                return Optional.empty();
            }
            String userId = rs.getString("user_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String imageUrl = rs.getString("image_url");
            return Optional.of(new User(userId, password, name, email, imageUrl));
        } catch (Exception e) {
            throw new RuntimeException("Failed to map user row", e);
        }
    }
}
