package application.dto.response;

import java.util.List;

public record ArticleResponse(
        String title,
        String content,
        String nickname,
        String imageUrl,
        List<String> images,
        int likes,
        CommentResponse comments

) {

}
