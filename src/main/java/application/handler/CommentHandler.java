package application.handler;

import static application.config.argumentresolver.AuthMemberArgumentResolver.SESSION_ID_COOKIE_KEY;

import application.config.argumentresolver.AuthMember;
import application.dto.request.CommentCreateRequest;
import application.exception.CustomAuthException;
import application.model.User;
import application.service.AuthService;
import application.service.CommentService;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import webserver.argumentresolver.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class CommentHandler {

    private final AuthService authService = new AuthService();
    private final CommentService commentService = new CommentService();

    @RequestMapping(method = HttpMethod.GET, path = "/comment")
    public HttpResponse commentPage(HttpRequest request) {
        try {
            if (!request.hasCookie(SESSION_ID_COOKIE_KEY)) {
                throw new CustomAuthException("/login/index.html");
            }
            RequestCookie requestCookie = request.getRequestCookie();
            User user = authService.authroize(requestCookie.get(SESSION_ID_COOKIE_KEY));
            HttpResponse response = new HttpResponse("/comment/index.html");
            response.addModelAttributes("account", user.getName());
            response.addModelAttributes("profileImage", user.getImageUrl());
            return response;
        } catch (Exception e) {
            return HttpResponse.redirect("/login");
        }
    }

    @RequestMapping(method = HttpMethod.POST, path = "/comment")
    public HttpResponse createComment(
            @AuthMember User user,
            @RequestBody CommentCreateRequest commentCreateRequest) {

        commentService.save(user, commentCreateRequest);
        return HttpResponse.ok();
    }
}
