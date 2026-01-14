package application.repository;

import application.model.Comment;
import java.util.List;

public interface CommentRepository {

    List<Comment> findByArticleId(long articleId);

    Comment save(Comment comment);
}
