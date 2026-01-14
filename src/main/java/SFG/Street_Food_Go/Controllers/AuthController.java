package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Security.jwt.JwtService;
import SFG.Street_Food_Go.Security.jwt.DTO.LoginRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; // Προσθήκη του Manager

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Εδω δημιουργειται το key / token
    @PostMapping("/client-tokens")
    public Map<String, String> login(@RequestBody LoginRequestDTO request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails user = (UserDetails) auth.getPrincipal();

            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> {
                        if (role.startsWith("ROLE_")) {
                            return role.substring(5); // Επιστρεφει "OWNER"
                        } else {
                            return role;
                        }
                    })
                    .collect(Collectors.toList());

            String token = jwtService.issue(user.getUsername(), roles);

            return Map.of("token", token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    // Εδω γινεται ο ελεγχος του key / token
    @GetMapping("/test-secure")
    public String testSecureEndpoint() {
        return "Authenticated successfully with JWT!";
    }
}
