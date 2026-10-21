package jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecretController {

    @PostMapping("/getSecret")
    public ResponseEntity<Map<String, String>> getSecret() {
        return ResponseEntity.ok(Map.of("message", "this is secret from server, uuuuuhh"));
    }
}
