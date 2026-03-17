package admin.panel.security;

import admin.panel.entity.vendor.VendorUser;
import admin.panel.repository.vendor.VendorUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final VendorUserRepository vendorUserRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            if (jwtUtil.isTokenValid(jwt) && "access".equals(jwtUtil.extractTokenType(jwt))) {
                String email = jwtUtil.extractEmail(jwt);
                String role = jwtUtil.extractRole(jwt);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Optional<VendorUser> userOpt = vendorUserRepository.findByEmail(email);

                    if (userOpt.isPresent() && userOpt.get().getIsActive()) {
                        VendorUser user = userOpt.get();

                        List<SimpleGrantedAuthority> authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_" + role)
                        );

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(user, null, authorities);

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            // Invalid token — just continue without auth
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/vendor/auth/") ||
               path.startsWith("/api/vendor/forms") ||
               path.startsWith("/uploads/") ||
               path.equals("/api/vendor/auth/verify-email");
    }
}