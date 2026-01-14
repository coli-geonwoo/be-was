package application.model;

public class ArticleLikes {

    private final long articleId;
    private final long count;

    public ArticleLikes(long articleId, long count) {
        this.articleId = articleId;
        this.count = count;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getCount() {
        return count;
    }
}
