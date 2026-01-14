package application.service;

import application.config.RepositoryConfig;
import application.dto.request.CommentCreateRequest;
import application.dto.response.CommentContentResponse;
import application.dto.response.CommentResponse;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.Comment;
import application.model.User;
import application.repository.CommentRepository;
import application.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

public class CommentService {

    private final CommentRepository commentRepository = RepositoryConfig.commentRepository();
    private final UserRepository userRepository = RepositoryConfig.userRepository();

    public CommentResponse findByArticleId(long articleId) {
        List<CommentContentResponse> contentResponses = commentRepository.findByArticleId(articleId)
                .stream()
                .map(com -> new CommentContentResponse(findById(com.getUserId()), com))
                .toList();
        return new CommentResponse(contentResponses.size(), contentResponses);
    }

    private User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public Comment save(User user, CommentCreateRequest commentCreateRequest) {
        Comment comment = new Comment(
                commentCreateRequest.getArticleId(),
                user.getUserId(),
                commentCreateRequest.getContent(),
                LocalDateTime.now()
        );
        return commentRepository.save(comment);
    }
}
