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
        String title = getMultipartFile(multipartFiles, "title", 0).getValue();
        String content = getMultipartFile(multipartFiles, "content", 0).getValue();
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest(title, content);
        Article savedArticle = articleService.save(user, articleCreateRequest);
        multipartFiles.getFiles("images")
                .stream()
                .filter(image -> !image.isFormField())
                .forEach(image -> articleImageService.saveImage(savedArticle, image));
    }

    private MultipartFile getMultipartFile(MultipartFiles multipartFiles, String fileName, int index) {
        List<MultipartFile> files = multipartFiles.getFiles(fileName);
        if(files.size() <= index) {
            throw new CustomException(ErrorCode.INVALID_MULTIPART_FILE);
        }
        return files.get(index);
    }
}
