package application.dto.request;

public class CommentCreateRequest {

    private long articleId;
    private String content;

    public CommentCreateRequest(long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    private CommentCreateRequest() {}

    public String getContent() {
        return content;
    }

    public long getArticleId() {
        return articleId;
    }
}
