package application.dto.response;

import application.model.Article;
import application.model.ArticleImage;
import application.model.User;
import java.util.List;

public record ArticleResponse(
        int total,
        String title,
        String content,
        String nickname,
        String imageUrl,
        List<String> images,
        long likes,
        CommentResponse comments

) {

    public ArticleResponse(int total, Article article, User user, List<ArticleImage> images, long likes, CommentResponse commentResponse) {
        this(
                total,
                article.getTitle(),
                article.getContent(),
                user.getName(),
                user.getImageUrl(),
                images.stream().map(ArticleImage::getImageUrl).toList(),
                likes,
                commentResponse
        );
    }

}
