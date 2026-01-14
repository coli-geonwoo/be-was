package application.repository.impl.h2;

import application.model.ArticleImage;
import application.model.Comment;
import application.repository.CommentRepository;
import application.repository.rowmapper.ArticleImagesRowMapper;
import application.repository.rowmapper.SelectCommentsRowMapper;
import db.JdbcTemplate;
import db.RowMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentH2Database implements CommentRepository {

    private final Logger logger = LoggerFactory.getLogger(ArticleImageH2Database.class);
    private final RowMapper<List<Comment>> commentsRowMapper = new SelectCommentsRowMapper();
    private final JdbcTemplate jdbcTemplate;

    public CommentH2Database(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findByArticleId(long articleId) {
        String sql = "select * from comments where article_id = ? order by created_at desc";
        return jdbcTemplate.executeQuery(sql, commentsRowMapper, articleId);
    }
}
