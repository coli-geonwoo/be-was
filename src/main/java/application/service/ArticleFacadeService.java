package application.service;

import application.dto.request.ArticleCreateRequest;
import application.dto.response.ArticleResponse;
import application.dto.response.CommentResponse;
import application.dto.response.LikesResponse;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.Article;
import application.model.ArticleImage;
import application.model.User;
import java.util.List;
import webserver.argumentresolver.MultipartFile;
import webserver.argumentresolver.MultipartFiles;

public class ArticleFacadeService {

    private final ArticleService articleService;
    private final ArticleImageService articleImageService;
    private final CommentService commentService;
    private final UserService userService;

    public ArticleFacadeService() {
        this.articleService = new ArticleService();
        this.articleImageService = new ArticleImageService();
        this.commentService = new CommentService();
        this.userService = new UserService();
    }

    public void save(MultipartFiles multipartFiles, User user) {
        String title = multipartFiles.getFirstFileValue("title");
        String content = multipartFiles.getFirstFileValue("content");
        List<MultipartFile> images = multipartFiles.getFiles("images");
        if (images.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_ARTICLE_INPUT);
        }
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(title, content);
        Article savedArticle = articleService.save(user, articleCreateRequest);
        images.stream()
                .filter(image -> !image.isFormField())
                .forEach(image -> articleImageService.saveImage(savedArticle, image));
    }

    public ArticleResponse getLatestArticle(int offset) {
        int total = articleService.count();
        if (total <= offset) {
            throw new CustomException(ErrorCode.INVALID_LATEST_ARTICLE_REQUEST);
        }
        Article article = articleService.findLatestArticle(offset);
        User user = userService.findById(article.getUserId());
        List<ArticleImage> images = articleImageService.findByArticleId(article.getId());
        long likes = articleService.countArticleLikes(article.getId());
        CommentResponse commentResponse = commentService.findByArticleId(article.getId());

        return new ArticleResponse(total, article, user, images, likes, commentResponse);
    }


    public LikesResponse incrementLikes(long articleId) {
        long likesCount = articleService.incrementAndGetLikes(articleId);
        return new LikesResponse(likesCount);
    }
}
