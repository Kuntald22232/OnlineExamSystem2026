package in.java.oes2026.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // ✅ PUBLIC ROUTES
        if (path.startsWith("/api/auth")
                || path.startsWith("/api/upcoming-exam")
                || path.startsWith("/uploads")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        // ❌ NO TOKEN → continue safely
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token = authHeader.substring(7);

            // extract username once (OPTIMIZED)
            String usernameFromToken = jwtService.extractUsername(token);

            // ⚡ validate token
            if (!jwtService.isTokenValid(token, usernameFromToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            // already authenticated skip
            if (usernameFromToken != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(usernameFromToken);

                String role = jwtService.extractRole(token);

                // 🔥 FIXED: proper Spring authority
                if (role == null) {
                    role = "ROLE_STUDENT";
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // DEBUG LOGS
                System.out.println("USERNAME => " + usernameFromToken);
                System.out.println("ROLE => " + role);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {

            System.out.println("JWT Filter Error: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}