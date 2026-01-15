package application.handler;

import static application.config.argumentresolver.AuthMemberArgumentResolver.SESSION_ID_COOKIE_KEY;

import application.config.argumentresolver.AuthMember;
import application.dto.response.LatestArticleResponse;
import application.dto.response.LikesResponse;
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

    @RequestMapping(method = HttpMethod.GET, path= "/article/latest")
    public HttpResponse getLatestArticle(HttpRequest request) {
        String offset = request.getRequestParameter("offset");
        LatestArticleResponse latestArticle = articleFacadeService.getLatestArticle(Integer.valueOf(offset));
        String response = getJsonResponse(latestArticle);
        return new HttpResponse(new HttpResponseBody(response.getBytes()));
    }

    @RequestMapping(method = HttpMethod.GET, path ="/article/id")
    public HttpResponse getArticleById(HttpRequest request) {
        String id = request.getRequestParameter("articleId");
        long offSet = articleFacadeService.getArticleOffSetById(Long.parseLong(id));
        return HttpResponse.redirect("/article/latest?=offset" + offSet);
    }

    @RequestMapping(method = HttpMethod.POST, path = "/article")
    public HttpResponse save(
            @AuthMember User user,
            @RequestBody MultipartFiles multipartFiles
    ) {
        articleFacadeService.save(multipartFiles, user);
        return HttpResponse.redirect("/"); //TODO 201로 전환
    }

    @RequestMapping(method = HttpMethod.POST, path= "/article/likes")
    public HttpResponse updateLikes(HttpRequest request) {
        long articleId = Long.parseLong(request.getRequestParameter("articleId"));
        LikesResponse likesResponse = articleFacadeService.incrementLikes(articleId);
        String response = getJsonResponse(likesResponse);
        return new HttpResponse(new HttpResponseBody(response.getBytes()));
    }

    private String getJsonResponse(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
