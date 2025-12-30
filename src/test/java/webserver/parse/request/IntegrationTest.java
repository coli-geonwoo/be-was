package webserver.parse.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.WebServer;

public class IntegrationTest {

    @BeforeAll
    public static void beforeAll() throws Exception {
        String [] port = {"8081"};
        new Thread(() -> {
            try {
                WebServer.main(port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(500);
    }

    @DisplayName("정적 파일을 반환할 수 있다")
    @Test
    void staticFile() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/index.html"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }
}
