package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Services.DTO.ErrorStatusCodeResultDTO;
import SFG.Street_Food_Go.Services.HandleErrorPage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorControllerUI implements ErrorController {
    private HandleErrorPage handleErrorPage;

    public MyErrorControllerUI(HandleErrorPage handleErrorPage) {this.handleErrorPage = handleErrorPage;}
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ErrorStatusCodeResultDTO result = handleErrorPage.getErrorStatusCode(request);

        model.addAttribute("errorCode",result.getStatusCode());
        model.addAttribute("errorMessage",result.getErrorMessage());
        return "error";
    }
}
