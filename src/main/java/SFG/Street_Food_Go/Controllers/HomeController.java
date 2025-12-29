package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    private PersonService personService;

    public HomeController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String home() {
        return "homepage";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal PersonDetails user, Model model) {
        Person person = personService.getPersonById(user.getPersonId());
        model.addAttribute("person", person);
        return "owner_dashboard";
    }

}
