package http.request;

import java.util.Map;

public class RequestUrl {

    private final String url;
    private final Map<String, String> params;

    public RequestUrl(String url, Map<String, String> params) {
        this.url = url;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getParamByKey(String parameterName) {
        return params.get(parameterName);
    }
}
