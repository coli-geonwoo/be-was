package application.service;

import application.dto.request.ArticleCreateRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.Article;
import application.model.User;
import java.util.List;
import webserver.argumentresolver.MultipartFile;
import webserver.argumentresolver.MultipartFiles;

public class ArticleFacadeService {

    private final ArticleService articleService;
    private final ArticleImageService articleImageService;

    public ArticleFacadeService() {
        this.articleService = new ArticleService();
        this.articleImageService = new ArticleImageService();
    }

    public void save(MultipartFiles multipartFiles, User user) {
        String title = multipartFiles.getFirstFileValue("title");
        String content = multipartFiles.getFirstFileValue("content");
        List<MultipartFile> images = multipartFiles.getFiles("images");
        if(images.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_ARTICLE_INPUT);
        }
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(title, content);
        Article savedArticle = articleService.save(user, articleCreateRequest);
        images.stream()
                .filter(image -> !image.isFormField())
                .forEach(image -> articleImageService.saveImage(savedArticle, image));
    }
}
