package application.repository.rowmapper;

import application.model.User;
import db.RowMapper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SelectUsersRowMapper implements RowMapper<List<User>> {

    @Override
    public List<User> mapRow(ResultSet rs) {
        try {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String imageUrl = rs.getString("image_url");
                users.add(new User(userId, password, name, email, imageUrl));
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
