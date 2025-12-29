package SFG.Street_Food_Go.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
Inspired by: https://www.geeksforgeeks.org/advance-java/redirect-to-different-pages-after-login-with-spring-security/
Adapted According To Our Needs
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)// Authentication has user info
                                        throws IOException, ServletException {

        String redirectURL="";

        // If User Has  ROLE_OWNER 'if statement' becomes true and goes to owner's dashboard
        // Else if goes to available restaurants
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
            redirectURL = "/dashboard";
        }
        else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            redirectURL = "/restaurants";
        }

        //Use Servlets sendRedirect method back to the user
        response.sendRedirect(redirectURL);
    }
}
