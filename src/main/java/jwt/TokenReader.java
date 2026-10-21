package jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class TokenReader {

    // Finds the token in the request, or returns null if there is none
    public static String getToken(HttpServletRequest request) {
        // TODO trin 6: find også tokenet i Authorization-headeren

        // a browser sends the token in the cookie named "token"
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
