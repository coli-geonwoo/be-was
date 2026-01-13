package application.service;

import application.config.argumentresolver.RepositoryConfig;
import application.model.ArticleImage;
import application.repository.ArticleImageRepository;
import application.repository.ArticleRepository;
import application.util.FileUploader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import webserver.argumentresolver.MultipartFile;

public class ArticleImageService {

    private final ArticleImageRepository imageRepository = RepositoryConfig.articleImageRepository();
    private final FileUploader fileUploader = new FileUploader();

    public void saveImage(MultipartFile image) {
//        String fileName = UUID.randomUUID().toString();
//        String imageUrl = "/uploads/images/" + fileName;
//        Path uploadPath = Paths.get("src/main/resources/static/uploads/images/");
//
//        imageRepository.save(images);
    }
}
