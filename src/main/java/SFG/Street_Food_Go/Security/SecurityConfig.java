package SFG.Street_Food_Go.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/js/**", "/css/**","/menu/**","/register","/login").permitAll()
                        .requestMatchers("/orders/**").authenticated()
                        .requestMatchers("/viewOrder/**").authenticated()
                        .requestMatchers("/product/**").hasRole("OWNER")
                        .requestMatchers("/menu/**").hasRole("CUSTOMER")
                        //uncomment once we create the custom redirection class once a user logged in
                        // users goes to /restaurants  and owner goes to /restaurant/{rest_id}
                        //.requestMatchers("/restaurants/**").access(new WebExpressionAuthorizationManager("isAnonymous() or hasRole('CUSTOMER')"))
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/restaurants", true)
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

} 