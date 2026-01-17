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