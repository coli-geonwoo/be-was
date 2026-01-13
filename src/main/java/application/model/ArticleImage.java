package application.model;

import java.util.UUID;

public class ArticleImage {

    private final String id;
    private final String articleId;
    private final String imageUrl;

    public ArticleImage(String id, String articleId, String imageUrl) {
        this.id = id;
        this.articleId = articleId;
        this.imageUrl = imageUrl;
    }

    public ArticleImage(String articleId, String imageUrl) {
        this(UUID.randomUUID().toString(), articleId, imageUrl);
    }

    public String getId() {
        return id;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
