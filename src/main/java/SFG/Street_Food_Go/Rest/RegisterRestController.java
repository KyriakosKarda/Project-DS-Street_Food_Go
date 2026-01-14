package SFG.Street_Food_Go.Rest;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Rest.DTO.RegisterDTO;
import SFG.Street_Food_Go.Rest.Mapper.PersonMapper;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.models.PersonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RegisterRestController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    public RegisterRestController(PersonService personService, PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDTO registerDTO) {
        Person person = personMapper.registerDtoToPerson(registerDTO);

        PersonResult result = personService.createPerson(person);

        if (result.isCreated()) {
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", result.getErrorMessage()));
        }
    }
}