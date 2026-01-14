package application.repository.impl.h2;

import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.User;
import application.repository.UserRepository;
import db.JdbcTemplate;
import db.RowMapper;
import application.repository.rowmapper.SelectUserRowMapper;
import application.repository.rowmapper.SelectUsersRowMapper;
import java.util.List;
import java.util.Optional;

public class UserH2Database implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Optional<User>> userRowMapper = new SelectUserRowMapper();
    private final RowMapper<List<User>> usersRowMapper = new SelectUsersRowMapper();

    public UserH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (user_id, name, password, email, image_url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, user.getUserId(), user.getName(), user.getPassword(), user.getEmail(), user.getImageUrl());
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.executeQuery(sql, userRowMapper, id);
    }

    @Override
    public Optional<User> findByName(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        return jdbcTemplate.executeQuery(sql, userRowMapper, name);
    }

    @Override
    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND password = ?";
        return jdbcTemplate.executeQuery(sql, userRowMapper, userId, password);
    }

    @Override
    public User updateUserInfo(String userId, String nickname, String password, String imageUrl) {
        String sql = """
                UPDATE users
                SET name = COALESCE(?, name),
                    password = COALESCE(?, password),
                    image_url = COALESCE(?, image_url)
                WHERE user_id = ?
                """;
        jdbcTemplate.executeUpdate(sql, nickname, password, imageUrl, userId);

        return findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.executeQuery(sql, usersRowMapper);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM users";
        jdbcTemplate.executeUpdate(sql);
    }
}
