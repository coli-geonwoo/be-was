package application.dto.response;

public record CommentContentResponse(
        String nickname,
        String imageUrl,
        String content
) {

}
