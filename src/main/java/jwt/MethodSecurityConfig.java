package jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// Turns on @PreAuthorize, so a single endpoint can say who may call it
@Configuration
@EnableMethodSecurity
public class MethodSecurityConfig {
}
