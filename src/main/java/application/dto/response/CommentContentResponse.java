package application.dto.response;

import application.model.Comment;
import application.model.User;

public record CommentContentResponse(
        String nickname,
        String imageUrl,
        String content
) {

    public CommentContentResponse(User user, Comment comment) {
        this(user.getName(), user.getImageUrl(), comment.getContent());
    }
}
