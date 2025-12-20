package SFG.Street_Food_Go.Controllers;


import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private PersonService  personService;

    public LoginController(PersonService personService) {this.personService = personService;}

    @GetMapping("/login")
    public String login(Model model){
        Person person = new Person();
        model.addAttribute("person",person);
        return "login";
    }

//    @PostMapping("/login")
//    public String handleLoginForm(@ModelAttribute Person person,Model model){
//        boolean exists = personService.personExists(person);
//        System.out.println(exists);
//
//        if(exists){
//            System.out.println("Person Exists "+ person.getName() + " "+ person.getPasswordHash());
//            return "redirect:/restaurants";
//        }
//        System.out.println("Person Not Exists ");
//        model.addAttribute("error","?error");
//        return "login";
//
//    }
}
