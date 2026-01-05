package http.response;

import java.util.Map;

public class Cookie {

    public static final Cookie EXPIRED_COOKIE = new Cookie(Map.of(), "/", 0);

    private final Map<String, String> contents;
    private final String path;
    private final int maxAge;

    public Cookie(Map<String, String> contents, String path, int maxAge) {
        this.contents = contents;
        this.path = path;
        this.maxAge = maxAge;
    }

    public Map<String, String> getContents() {
        return contents;
    }

    public String getPath() {
        return path;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
