package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import application.db.Database;
import application.db.SessionDataBase;
import http.response.HttpStatusCode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void beforeEach() {
        Database.clear();
        SessionDataBase.clear();
    }

    @DisplayName("index.html을 반환할 수 있다")
    @Test
    void index() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3L))
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
                .timeout(Duration.ofSeconds(3L))
                .uri(URI.create("http://localhost:8081/registration"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode());
    }

    @DisplayName("create로 유저를 생성하면 /index.html로 리다이렉션 한다")
    @Test
    void create() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String body = "userId=javajigi&password=password&name=coli&email=email@email.com";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/user/create"))
                .timeout(Duration.ofSeconds(3))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        User foundUser = Database.findUserById("javajigi");

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.REDIRECTED.getCode()),
                () -> assertThat(response.headers().map().get("location")).contains("/index.html"),
                () -> assertThat(foundUser.getUserId()).isEqualTo("javajigi"),
                () -> assertThat(foundUser.getPassword()).isEqualTo("password"),
                () -> assertThat(foundUser.getName()).isEqualTo("coli"),
                () -> assertThat(foundUser.getEmail()).isEqualTo("email@email.com")
        );
    }
}
