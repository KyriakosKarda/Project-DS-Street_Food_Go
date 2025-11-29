package SFG.Street_Food_Go.Controllers;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.Restaurant;
import SFG.Street_Food_Go.Services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private PersonService personService;
    public RegisterController(PersonService personService) {this.personService = personService;}

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistrationForm(@ModelAttribute Person person){
        Person returned_person = personService.createPerson(person);
        return "login";
    }
}
