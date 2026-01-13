package application.service;

import application.config.RepositoryConfig;
import application.model.Article;
import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import application.util.FileUploader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import webserver.argumentresolver.MultipartFile;

public class ArticleImageService {

    private static final Path UPLOAD_PATH = Paths.get("src", "main", "resources", "static", "uploads", "images");

    private final ArticleImageRepository imageRepository = RepositoryConfig.articleImageRepository();
    private final FileUploader fileUploader = new FileUploader();

    public ArticleImage saveImage(Article article, MultipartFile image) {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path fullPath = UPLOAD_PATH.resolve(fileName);
        fileUploader.imageUpload(image.getInputStream(), fullPath);
        String imageUrl = "/uploads/images/" + fileName;
        ArticleImage articleImage = new ArticleImage(article.getId(), imageUrl);
        return imageRepository.save(articleImage);
    }
}
