package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Services.models.PersonResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> getAllPersons();
    PersonResult createPerson(Person person);
    boolean personExists(Person person);
    Person getPersonById(Long id);

    boolean personExistsById(Long id);
}
