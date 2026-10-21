package jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenManager jwtTokenManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenManager = jwtTokenManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody LoginRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "user exists"));
        }
        // save the bcrypt hash, never the clear-text password
        userRepository.save(new User(request.username(), passwordEncoder.encode(request.password())));
        return ResponseEntity.ok(Map.of("message", "created user: " + request.username()));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> user = userRepository.findByUsername(request.username());
        // bcrypt compares the clear-text password with the hash from the database
        if (user.isEmpty() || !passwordEncoder.matches(request.password(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "username or password incorrect"));
        }
        // TODO trin 2
        return ResponseEntity.ok(Map.of("message", "login success"));
    }

    private Cookie createTokenCookie(String token, int maxAgeSeconds) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true); // JavaScript in the browser can NOT read this cookie
        cookie.setPath("/"); // send the cookie to all endpoints
        cookie.setMaxAge(maxAgeSeconds);
        return cookie;
    }

    // ---------------------------------------------------------------------
    // Herunder: bruges først i trin 7
    // ---------------------------------------------------------------------

    @PostMapping("/logout2") // named "logout2" because Spring Security has "taken" /logout
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        // TODO trin 7
        return ResponseEntity.ok(Map.of("message", "logged out"));
    }
}
