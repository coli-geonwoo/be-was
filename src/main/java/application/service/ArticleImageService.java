package application.service;

import application.config.argumentresolver.RepositoryConfig;
import application.model.Article;
import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import application.repository.ArticleRepository;
import application.util.FileUploader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import webserver.argumentresolver.MultipartFile;

public class ArticleImageService {

    private static final String UPLOAD_PATH = "src/main/resources/static/uploads/images/";

    private final ArticleImageRepository imageRepository = RepositoryConfig.articleImageRepository();
    private final FileUploader fileUploader = new FileUploader();

    public ArticleImage saveImage(Article article, MultipartFile image) {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String fullPath = UPLOAD_PATH + fileName;
        fileUploader.imageUpload(image.getInputStream(), fullPath);
        String imageUrl = "/uploads/images/" + fileName;
        ArticleImage articleImage = new ArticleImage(article.getId(), imageUrl);
        return imageRepository.save(articleImage);
    }
}
