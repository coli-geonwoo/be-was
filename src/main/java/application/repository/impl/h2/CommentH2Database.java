package application.repository.impl.h2;

import application.model.Comment;
import application.repository.CommentRepository;
import application.repository.rowmapper.SelectCommentsRowMapper;
import db.JdbcTemplate;
import db.RowMapper;
import java.util.List;

public class CommentH2Database implements CommentRepository {

    private final RowMapper<List<Comment>> commentsRowMapper = new SelectCommentsRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public CommentH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findByArticleId(long articleId) {
        String sql = "select * from comments where article_id = ? order by created_at asc";
        return jdbcTemplate.executeQuery(sql, commentsRowMapper, articleId);
    }

    @Override
    public Comment save(Comment comment) {
        String sql = """
                    INSERT INTO comments (id, article_id, user_id, content, created_at)
                    VALUES (?, ?, ?, ?, ?)
                """;
        jdbcTemplate.executeUpdate(
                sql,
                comment.getId(),
                comment.getArticleId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
        return comment;
    }
}
