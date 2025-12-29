package SFG.Street_Food_Go.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/js/**", "/css/**","/register","/login","/menu/*/cart").permitAll()
                        .requestMatchers("/orders/**").hasRole("OWNER")
                        .requestMatchers("/menu/*").hasRole("OWNER")
                        .requestMatchers("/viewOrder/**").hasRole("CUSTOMER")
                        .requestMatchers("/order/restaurant/**").hasRole("OWNER")
                        .requestMatchers("/product/**").hasRole("OWNER")
                        .requestMatchers("/restaurant/**").hasRole("OWNER")
                        .requestMatchers("/dashboard").hasRole("OWNER")
                        .anyRequest().permitAll()
                )
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

} 