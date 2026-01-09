package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import application.db.Database;
import application.db.SessionDataBase;
import http.HttpStatusCode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    @DisplayName("로그인 상황에서 index.html을 반환한다")
    @Test
    void indexWhenLogin() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        User user = new User("userId", "password", "name", "email@email.com");
        Database.addUser(user);
        SessionDataBase.saveData(sessionId, user.getUserId());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(3L))
                .uri(URI.create("http://localhost:8081/index.html"))
                .setHeader("Cookie", "sid=" + sessionId)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode());
    }

    @DisplayName("로그인되어 있지 않은 상황에서 index.html을 반환한다")
    @Test
    void indexWhenLogOut() throws Exception {
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

    @DisplayName("로그인에 성공하면 세션 쿠키를 세팅하고 /index.html로 리다이렉션 한다")
    @Test
    void loginSuccess() throws Exception {
        User user = new User("userId", "password", "name", "email@email.com");
        Database.addUser(user);
        HttpClient client = HttpClient.newHttpClient();
        String body = "userId=" +  user.getUserId() + "&password=" + user.getPassword();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/login"))
                .timeout(Duration.ofSeconds(3))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, List<String>> responseHeader = response.headers().map();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.REDIRECTED.getCode()),
                () -> assertThat(responseHeader.get("location")).contains("/index.html"),
                () -> assertThat(responseHeader.get("set-cookie")).anyMatch(
                        value -> value.contains("sid=")
                )
        );
    }

    @DisplayName("로그인에 실패하면 401에러를 반환한다")
    @Test
    void loginFail() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String body = "userId=userId&password=password";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/login"))
                .timeout(Duration.ofSeconds(3))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.UNAUTHORIZED_401.getCode());
    }

    @DisplayName("로그아웃 하면 /index.html로 리다이렉션한다")
    @Test
    void logOut() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        User user = new User("userId", "password", "name", "email@email.com");
        Database.addUser(user);
        SessionDataBase.saveData(sessionId, user.getUserId());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/logout"))
                .timeout(Duration.ofSeconds(3))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .setHeader("Cookie", "sid=" + sessionId)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, List<String>> responseHeader = response.headers().map();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.REDIRECTED.getCode()),
                () -> assertThat(responseHeader.get("location")).contains("/index.html"),
                () -> assertThat(responseHeader.get("set-cookie")).anyMatch(
                        value -> value.contains("Max-Age=0")
                ),
                () -> assertThat(SessionDataBase.getData(sessionId)).isNotPresent()
        );
    }

    @DisplayName("로그인이 되어 있지 않은 상태에서 로그아웃 하면 index.html로 리다이렉션 한다")
    @Test
    void logOutFail() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/logout"))
                .timeout(Duration.ofSeconds(3))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, List<String>> responseHeader = response.headers().map();
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.REDIRECTED.getCode()),
                () -> assertThat(responseHeader.get("location")).contains("/index.html"),
                () -> assertThat(responseHeader.get("set-cookie")).anyMatch(
                        value -> value.contains("Max-Age=0")
                )
        );
    }

    @DisplayName("로그인이 되어 있는 상황에서 /mypage를 조회할 수 있다")
    @Test
    void myPageSuccess() throws Exception {
        String sessionId = UUID.randomUUID().toString();
        User user = new User("userId", "password", "name", "email@email.com");
        Database.addUser(user);
        SessionDataBase.saveData(sessionId, user.getUserId());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/mypage"))
                .timeout(Duration.ofSeconds(3))
                .setHeader("Cookie", "sid=" + sessionId)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpStatusCode.OK_200.getCode());
    }

    @DisplayName("로그인이 되어 있지 않은 상황에서는 /로 리다이렉션한다")
    @Test
    void myPageFail() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/mypage"))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        Map<String, List<String>> responseHeader = response.headers().map();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatusCode.REDIRECTED.getCode()),
                () -> assertThat(responseHeader.get("location")).contains("/index.html")
        );
    }
}
