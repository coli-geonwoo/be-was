package application.config.argumentresolver;

import application.exception.CustomAuthException;
import application.exception.CustomException;
import application.service.AuthService;
import http.request.HttpRequest;
import http.request.RequestCookie;
import java.lang.reflect.Parameter;
import webserver.argumentresolver.ArgumentResolver;

public class AuthMemberArgumentResolver implements ArgumentResolver {

    public static final String SESSION_ID_COOKIE_KEY = "sid";

    private final AuthService authService = new AuthService();

    @Override
    public boolean canConvert(HttpRequest request, Parameter parameter) {
        return parameter.isAnnotationPresent(AuthMember.class);
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        try {
            if (request.hasCookie(SESSION_ID_COOKIE_KEY)) {
                RequestCookie requestCookie = request.getRequestCookie();
                return authService.authroize(requestCookie.get(SESSION_ID_COOKIE_KEY));
            }
            throw new CustomAuthException();
        } catch (CustomException customException) {
            throw new CustomAuthException();
        }
    }
}
