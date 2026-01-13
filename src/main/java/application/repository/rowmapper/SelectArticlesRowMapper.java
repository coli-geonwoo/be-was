package application.repository.rowmapper;

import application.model.Article;
import db.RowMapper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SelectArticlesRowMapper implements RowMapper<List<Article>> {

    @Override
    public List<Article> mapRow(ResultSet rs) {
        try {
            List<Article> articles = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String userId = rs.getString("user_id");
                articles.add(new Article(id, title, content, userId));
            }
            return articles;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
