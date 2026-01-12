package db.h2;

import application.model.Article;
import application.repository.ArticleRepository;
import db.RowMapper;
import db.SqlRunner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ArticleH2Database implements ArticleRepository {

    @Override
    public Article save(Article article) {
        String sql = """
                    INSERT INTO articles (title, content, user_id)
                    VALUES (?, ?, ?)
                """;
        return (Article) SqlRunner.executeQuery(connection -> {
                    try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                        ps.setString(1, article.getTitle());
                        ps.setString(2, article.getContent());
                        ps.setString(3, article.getUserId());
                        ps.executeUpdate();
                        ResultSet resultSet = ps.getGeneratedKeys();
                        if (resultSet.next()) {
                            long id = resultSet.getLong(1);
                            return new Article(
                                    id,
                                    article.getTitle(),
                                    article.getContent(),
                                    article.getUserId()
                            );
                        }
                        throw new SQLException("Failed to insert new article");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public List<Article> findUserById(String userId) {
        String sql = "SELECT * FROM articles WHERE user_id = ?";
        RowMapper<List<Article>> articleRowMapper = null;
        return (List<Article>) SqlRunner.executeQuery(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet resultSet = ps.executeQuery(sql);
                if (resultSet.next()) {
                    return articleRowMapper.mapRow(resultSet);
                }
                throw new SQLException("Failed to find articles with user_id: " + userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM articles";
        SqlRunner.executeUpdate(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                return ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
