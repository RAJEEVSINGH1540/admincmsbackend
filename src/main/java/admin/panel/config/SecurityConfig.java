package admin.panel.config;

import admin.panel.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Vendor auth
                        .requestMatchers("/api/vendor/auth/**").permitAll()

                        // Vendor forms (public read, CMS write)
                        .requestMatchers("/api/vendor/forms/**").permitAll()

                        // Vendor users (CMS)
                        .requestMatchers("/api/vendor/users/**").permitAll()

                        // Vendor submissions
                        .requestMatchers("/api/vendor/submissions/**").permitAll()

                        // Existing pages
                        .requestMatchers("/api/contact/**").permitAll()
                        .requestMatchers("/api/home/**").permitAll()
                        .requestMatchers("/api/about/**").permitAll()
                        .requestMatchers("/api/gallery/**").permitAll()
                        .requestMatchers("/api/media/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api/vision/**").permitAll()
                        .requestMatchers("/api/media-events/**").permitAll()
                        .requestMatchers("/api/journey/**").permitAll()
                        .requestMatchers("/api/services/**").permitAll()

                        // Protected
                        .requestMatchers("/api/vendor/profile/**").authenticated()

                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}