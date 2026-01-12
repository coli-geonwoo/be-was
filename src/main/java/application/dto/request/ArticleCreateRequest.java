package application.dto.request;

public class ArticleCreateRequest {

    private String title;
    private String content;

    public ArticleCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleCreateRequest() {
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
