package jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecretController {

    @PostMapping("/getSecret")
    public ResponseEntity<Map<String, String>> getSecret() {
        return ResponseEntity.ok(Map.of("message", "this is secret from server, uuuuuhh"));
    }

    // Only a request whose token has the role "admin" gets in; everyone else who is logged in gets 403 Forbidden
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/admin")
    public ResponseEntity<Map<String, String>> admin() {
        return ResponseEntity.ok(Map.of("message", "hello admin"));
    }
}
