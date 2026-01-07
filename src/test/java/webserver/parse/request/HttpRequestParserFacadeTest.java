package webserver.parse.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import http.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;
import http.HttpVersion;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;

class HttpRequestParserFacadeTest {

    @Test
    void parseGetRequest() throws IOException {
        String rawRequest = "GET /index.html HTTP/1.1\r\n" +
                        "User-Agent: PostmanRuntime/7.51.0\r\n" +
                        "Accept: */*\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Accept-Encoding: gzip, deflate, br\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes())));
        HttpRequestParserFacade requestParserFacade = new HttpRequestParserFacade();
        HttpRequest request = requestParserFacade.parse(bufferedReader);

        HttpRequestLine requestLine = request.getRequestLine();
        HttpRequestHeader requestHeader = request.getRequestHeader();
        HttpRequestBody requestBody = request.getRequestBody();

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(requestLine.getVersion()).isEqualTo(HttpVersion.HTTP_1_1),
                () -> assertThat(request.getRequestUrl()).isEqualTo("/index.html"),
                () -> assertThat(requestHeader.getHeaderContent("User-Agent")).isEqualTo("PostmanRuntime/7.51.0"),
                () -> assertThat(requestHeader.getHeaderContent("Accept")).isEqualTo("*/*"),
                () -> assertThat(requestHeader.getHeaderContent("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(requestHeader.getHeaderContent("Accept-Encoding")).isEqualTo("gzip, deflate, br"),
                () -> assertThat(requestHeader.getHeaderContent("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(requestBody).isEqualTo(HttpRequestBody.EMPTY_REQUEST_BODY)
        );
    }

    @Test
    void parsePostRequest() throws IOException {
        String rawRequest = "POST /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 64\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n" +
                "userId=coli&password=password&name=GeonWoo&email=email@email.com";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawRequest.getBytes())));

        HttpRequestParserFacade requestParserFacade = new HttpRequestParserFacade();
        HttpRequest request = requestParserFacade.parse(bufferedReader);

        HttpRequestLine requestLine = request.getRequestLine();
        HttpRequestHeader requestHeader = request.getRequestHeader();
        HttpRequestBody requestBody = request.getRequestBody();
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(requestLine.getVersion()).isEqualTo(HttpVersion.HTTP_1_1),
                () -> assertThat(request.getRequestUrl()).isEqualTo("/user/create"),
                () -> assertThat(requestHeader.getHeaderContent("Content-Type")).isEqualTo(
                        "application/x-www-form-urlencoded"),
                () -> assertThat(requestHeader.getHeaderContent("Content-Length")).isEqualTo("64"),
                () -> assertThat(requestHeader.getHeaderContent("Host")).isEqualTo("localhost:8080"),
                () -> assertThat(requestHeader.getHeaderContent("Connection")).isEqualTo("keep-alive"),
                () -> assertThat(requestBody.getValue()).isEqualTo(
                        "userId=coli&password=password&name=GeonWoo&email=email@email.com")
        );
    }
}
