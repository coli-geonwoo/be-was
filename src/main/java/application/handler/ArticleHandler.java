package application.handler;

import static application.config.argumentresolver.AuthMemberArgumentResolver.SESSION_ID_COOKIE_KEY;

import application.config.argumentresolver.AuthMember;
import application.model.User;
import application.service.ArticleFacadeService;
import application.service.AuthService;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
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
        return HttpResponse.ok(); //TODO 201로 전환
    }
}
