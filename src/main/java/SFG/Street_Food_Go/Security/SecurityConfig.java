package SFG.Street_Food_Go.Security;

import SFG.Street_Food_Go.Security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public  SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {this.jwtAuthenticationFilter = jwtAuthenticationFilter;}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http
                     .csrf(AbstractHttpConfigurer::disable)

                     // το αλλαξα για να μην μπερδευεται με τα αρχεια που εχουμε για τα errors
                     .exceptionHandling(exception -> exception
                             .defaultAuthenticationEntryPointFor(
                                     new HttpStatusEntryPoint(HttpStatus.FORBIDDEN),
                                     request -> request.getServletPath().startsWith("/api/")
                             )
                     )

                     // ελεγχω αν το συγκεκριμενο api θες να ανοιγει browser ή μονο στο swagger / postman.
                     .addFilterBefore(new OncePerRequestFilter() {
                         @Override
                         protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
                             String path = request.getRequestURI();
                             String acceptHeader = request.getHeader("Accept");
                             boolean isBrowser = acceptHeader != null && acceptHeader.contains("text/html");

                             if (path.startsWith("/api/v1/")) {
                                 boolean isAllowedForBrowser =
                                         path.contains("/auth/client-login") ||
                                                 path.contains("/auth/client-tokens") ||
                                                 path.contains("/register") ||
                                                 path.contains("/restaurants") ||
                                                 path.contains("/menu");

                                 // εκανα αλλαγες για το custom response body
                                 if (!isAllowedForBrowser) {
                                     if (request.getHeader("Authorization") == null) {

                                         // αν πασ να κανεις access το endpoint απο το browser
                                         if (isBrowser) {
                                             response.sendError(403, "Access Denied");
                                             return;
                                         }

                                         // αν πασ να κανεις access το endpoint απο το swagger
                                         response.setStatus(403);
                                         response.setContentType("application/json");
                                         response.setCharacterEncoding("UTF-8");
                                         String jsonResponse = "{\"status\": 403, \"error\": \"Access Denied\", \"message\": \"Authorize first.\"}";

                                         response.getWriter().write(jsonResponse);
                                         response.flushBuffer();
                                         return;
                                     }
                                 }
                             }
                             chain.doFilter(request, response);
                         }
                     }, UsernamePasswordAuthenticationFilter.class)

                     .authorizeHttpRequests((requests) -> requests
                        //UI
                        .requestMatchers("/","/js/**", "/css/**","/register","/login","/menu/*/cart").permitAll()
                        .requestMatchers("/orders/**").hasRole("OWNER")
                        .requestMatchers("/menu/*").hasRole("OWNER")
                        .requestMatchers("/viewOrder/**").hasRole("CUSTOMER")
                        .requestMatchers("/order/restaurant/**").hasRole("OWNER")
                        .requestMatchers("/product/**").hasRole("OWNER")
                        .requestMatchers("/restaurant/**").hasRole("OWNER")
                        .requestMatchers("/dashboard").hasRole("OWNER")
                        //APIs
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html","/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v1/auth/client-tokens").permitAll()
                        .requestMatchers("/api/v1/register").permitAll()
                        .requestMatchers("/api/v1/auth/test-secure").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new AuthSuccessHandler())
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}