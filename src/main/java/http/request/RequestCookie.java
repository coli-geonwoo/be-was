package http.request;

import java.util.Map;

public class RequestCookie {

    public static final RequestCookie EMPTY_COOKIE = new RequestCookie(Map.of());

    private final Map<String, String> content;

    public RequestCookie(Map<String, String> content) {
        this.content = content;
    }

    public boolean containsKey(String key) {
        return content.containsKey(key);
    }

    public String get(String key) {
        return content.get(key);
    }

    public Map<String, String> getContent() {
        return content;
    }
}
