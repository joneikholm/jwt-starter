package jwt;

// The JSON body of /signup and /login: {"username": "...", "password": "..."}
public record LoginRequest(String username, String password) {
}
