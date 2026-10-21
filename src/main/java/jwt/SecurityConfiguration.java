package jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            // Which endpoints are open, and which require a valid token.
            // /error is where Spring goes when something fails: keep it open, or every error becomes a 401.
            .authorizeHttpRequests(auth -> auth
                    // TODO trin 4
                    .anyRequest().permitAll()
            )
            // No valid token -> answer 401 Unauthorized
            .exceptionHandling(ex -> ex.authenticationEntryPoint(
                    (request, response, e) -> response.sendError(401, "Unauthorized")))
            // STATELESS: the server remembers nothing between requests. The token is the only proof of who you are.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Run our JwtFilter on every request
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // bcrypt is used to hash passwords before they are saved in the database
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
