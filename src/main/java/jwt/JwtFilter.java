package jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Runs once for EVERY incoming request, before the controller.
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenManager jwtTokenManager;

    public JwtFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = TokenReader.getToken(request);
        if (token != null) {
            String username = jwtTokenManager.getUsernameFromToken(token);
            if (username != null) {
                // The token is valid: tell Spring Security "this request comes from <username>, who has <role>"
                var role = new SimpleGrantedAuthority("ROLE_" + jwtTokenManager.getRoleFromToken(token));
                var authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(role));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continue to the controller. If no user was set above, Spring Security answers 401 for locked endpoints.
        filterChain.doFilter(request, response);
    }
}
