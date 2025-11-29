package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> getAllPersons();
    Person createPerson(Person person);
    boolean personExists(Person person);
}
