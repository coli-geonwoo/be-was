package application.handler;

import static application.config.argumentresolver.AuthMemberArgumentResolver.SESSION_ID_COOKIE_KEY;

import application.config.argumentresolver.AuthMember;
import application.dto.response.ArticleResponse;
import application.model.User;
import application.service.ArticleFacadeService;
import application.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.ResponseCookie;
import webserver.argumentresolver.MultipartFiles;
import webserver.argumentresolver.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class ArticleHandler {

    private final AuthService authService = new AuthService();
    private final ArticleFacadeService articleFacadeService = new ArticleFacadeService();

    @RequestMapping(method = HttpMethod.GET, path = "/article")
    public HttpResponse getArticle(HttpRequest request) {
        if (request.hasCookie(SESSION_ID_COOKIE_KEY)) {
            RequestCookie requestCookie = request.getRequestCookie();
            authService.authroize(requestCookie.get(SESSION_ID_COOKIE_KEY));
            return new HttpResponse("/article/index.html");
        }
        HttpResponse unAuthorizedResponse = HttpResponse.redirect("/login/index.html");
        unAuthorizedResponse.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
        return unAuthorizedResponse;
    }

    @RequestMapping(method = HttpMethod.POST, path = "/article")
    public HttpResponse save(
            @AuthMember User user,
            @RequestBody MultipartFiles multipartFiles
    ) {
        articleFacadeService.save(multipartFiles, user);
        return HttpResponse.redirect("/"); //TODO 201로 전환
    }

    @RequestMapping(method = HttpMethod.GET, path= "/article/latest")
    public HttpResponse getLatestArticle(HttpRequest request) {
        String offset = request.getRequestParameter("offset");
        ArticleResponse latestArticle = articleFacadeService.getLatestArticle(Integer.valueOf(offset));
        String response = getJsonResponse(latestArticle);
        return new HttpResponse(new HttpResponseBody(response.getBytes()));
    }

    private String getJsonResponse(ArticleResponse articleResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(articleResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    {
  "total": 42,
  "article": {
    "title": "리액티브 시스템의 핵심 원칙",
    "content": "우리는 시스템 아키텍처에 대한 일관성 있는 접근이 필요하며, 필요한 모든 측면은 이미 개별적으로 인식되고 있다고 생각합니다. 즉, 응답이 잘 되고, 탄력적이며 유연하고 메시지 기반으로 동작하는 시스템입니다. 우리는 이것을 리액티브 시스템(Reactive Systems)라고 부릅니다.",
    "images": {
      "imageUrl": "https://cdn.example.com/articles/123/image1.jpg",
      "imageUrl2": "https://cdn.example.com/articles/123/image2.jpg",
      "imageUrl3": "https://cdn.example.com/articles/123/image3.jpg"
    },
    "likes": 128,
    "comments": {
      "count": 5,
      "contents": [
        {
          "nickname": "springDev",
          "imageurl": "https://cdn.example.com/profiles/springDev.png",
          "content": "리액티브 선언문의 핵심을 잘 정리한 글이네요 👍"
        },
        {
          "nickname": "backendKim",
          "imageurl": "https://cdn.example.com/profiles/backendKim.jpg",
          "content": "응답성과 탄력성을 분리해서 설명한 부분이 인상적이었습니다."
        },
        {
          "nickname": "cloudLee",
          "imageurl": "https://cdn.example.com/profiles/cloudLee.png",
          "content": "실무에서 어떻게 적용하는지도 궁금하네요."
        },
        {
          "nickname": "msaMaster",
          "imageurl": "https://cdn.example.com/profiles/msaMaster.jpg",
          "content": "메시지 기반이라는 부분이 특히 중요하다고 생각합니다."
        },
        {
          "nickname": "reactiveFan",
          "imageurl": "https://cdn.example.com/profiles/reactiveFan.png",
          "content": "좋은 글 감사합니다!"
        }
      ]
    }
  },
  "nickname": "acoount",
  "imageUrl": "https://cdn.example.com/profiles/acoount.jpg"
}

     */
}
