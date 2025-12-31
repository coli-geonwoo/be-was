package webserver.parse.request;

import http.request.RequestUrl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestUrlParser implements HttpRequestParser<RequestUrl> {

    private static final String HTTP_REQUEST_PARAMETER_DELIMITER = "&";
    private static final String HTTP_REQUEST_PARAMETER_STARTER = "\\?";
    private static final String PARAMETER_KEY_VALUE_DELIMITER = "=";

    @Override
    public RequestUrl parse(String input) {
        String [] splitUrl = input.split(HTTP_REQUEST_PARAMETER_STARTER);
        String url = splitUrl[0];

        if(splitUrl.length < 2) {
            return new RequestUrl(url, Map.of());
        }

        Map<String, String> params = resolveParameters(splitUrl[1]);
        return new RequestUrl(url, params);
    }

    private Map<String, String> resolveParameters(String parameters) {
        String [] parameterChunk = parameters.split(HTTP_REQUEST_PARAMETER_DELIMITER);
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < parameterChunk.length; i++) {
            String[] entrySet = parameterChunk[i].split(PARAMETER_KEY_VALUE_DELIMITER);
            validateLength(entrySet, 2);
            params.put(entrySet[0], entrySet[1]);
        }
        return params;
    }

    private void validateLength(String [] array, int length) {
        if(array.length != length) {
            throw new IllegalArgumentException("RequestUrl 파싱과정에서 문제가 생겼습니다" +  Arrays.toString(array));
        }
    }
}
