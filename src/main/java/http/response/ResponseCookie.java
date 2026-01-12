package http.response;

import http.SameSite;
import java.util.Map;

public class ResponseCookie {

    public static final ResponseCookie EXPIRED_RESPONSE_COOKIE = new ResponseCookie(Map.of(), "/", 0);

    private final Map<String, String> contents;
    private final String path;
    private final SameSite sameSite;
    private final boolean isHttpOnly;
    private final int maxAge;

    public ResponseCookie(Map<String, String> contents, String path, int maxAge) {
        this.sameSite = SameSite.LAX;
        this.isHttpOnly = true;
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

    public SameSite getSameSite() {
        return sameSite;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }
}
