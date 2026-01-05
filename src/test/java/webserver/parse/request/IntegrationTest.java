package webserver.parse.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import application.db.Database;
import http.response.HttpStatusCode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.WebServer;

public class IntegrationTest {

    @BeforeAll
    public static void beforeAll() throws Exception {
        String[] port = {"8081"};
        new Thread(() -> {
            try {
                WebServer.main(port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(500);
    }

    @DisplayName("index.html을 반환할 수 있다")
    @Test
    void index() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/index.html"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode());
    }

    @DisplayName("registration.html을 반환할 수 있다")
    @Test
    void registration() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/registration"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode());
    }

    @DisplayName("create로 유저를 생성할 수 있다")
    @Test
    void create() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/create?userId=javajigi&password=password&name=coli&email=email@email.com"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        User foundUser = Database.findUserById("javajigi");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode()),
                () -> assertThat(foundUser.getUserId()).isEqualTo("javajigi"),
                () -> assertThat(foundUser.getPassword()).isEqualTo("password"),
                () -> assertThat(foundUser.getName()).isEqualTo("coli"),
                () -> assertThat(foundUser.getEmail()).isEqualTo("email@email.com")
        );
    }
}
