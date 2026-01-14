package application.repository.rowmapper;

import application.model.Article;
import application.model.ArticleImage;
import application.model.Comment;
import db.RowMapper;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SelectCommentsRowMapper implements RowMapper<List<Comment>> {

    @Override
    public List<Comment> mapRow(ResultSet rs) {
        try {
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                long articleId = rs.getLong("article_id");
                String content = rs.getString("content");
                String userId = rs.getString("user_id");
                LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
                comments.add(new Comment(id, articleId, userId, content, createdAt));
            }
            return comments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
