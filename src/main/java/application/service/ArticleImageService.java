package application.service;

import application.config.RepositoryConfig;
import application.model.Article;
import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import application.util.FileUploader;
import webserver.argumentresolver.MultipartFile;

public class ArticleImageService {


    private final ArticleImageRepository imageRepository = RepositoryConfig.articleImageRepository();
    private final FileUploader fileUploader = new FileUploader();

    public ArticleImage saveImage(Article article, MultipartFile image) {
        String savedPath = fileUploader.imageUpload(image.getInputStream(), image.getOriginalFilename());
        ArticleImage articleImage = new ArticleImage(article.getId(), savedPath);
        return imageRepository.save(articleImage);
    }
}
