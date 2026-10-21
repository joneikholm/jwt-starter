package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenManager {
    public static final long TOKEN_VALIDITY = 60 * 60 * 1000; // 1 hour, in milliseconds

    private final Key key;

    // The secret only exists on the server. It is used both to SIGN new tokens and to CHECK incoming tokens.
    public JwtTokenManager(@Value("${secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Called at login: creates a signed token that says "this is <username>, valid until <expiration>"
    public String generateToken(String username) {
        // TODO trin 3: byg et rigtigt token. Indtil da sendes bare denne tekst.
        return "ikke-et-token-endnu";
    }

    // ---------------------------------------------------------------------
    // Herunder: bruges først i trin 5
    // ---------------------------------------------------------------------

    // Called on every request: the username from the token, or null if the token is not valid
    public String getUsernameFromToken(String token) {
        // TODO trin 5
        return null;
    }
}
