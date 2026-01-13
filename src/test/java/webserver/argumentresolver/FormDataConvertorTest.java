package webserver.argumentresolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import application.dto.request.LoginRequest;
import http.HttpMethod;
import http.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.request.RequestCookie;
import http.request.RequestUrl;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormDataConvertorTest {


    @DisplayName("폼 형식의 데이터를 객체로 전환할 수 있다")
    @Test
    void resolve() {
        FormDataConvertor convertor = new FormDataConvertor();
        HttpRequest request = new HttpRequest(
                new HttpRequestLine(HttpMethod.POST, new RequestUrl("/example", Map.of()), HttpVersion.HTTP_1_1),
                new HttpRequestHeader(Map.of()),
                new HttpRequestBody("userId=coli&password=pass1234".getBytes()),
                RequestCookie.EMPTY_COOKIE
        );

        LoginRequest result = (LoginRequest) convertor.resolve(request, LoginRequest.class);

        assertAll(
                () -> assertThat(result.getPassword()).isEqualTo("pass1234"),
                () -> assertThat(result.getUserId()).isEqualTo("coli")
        );
    }
}
