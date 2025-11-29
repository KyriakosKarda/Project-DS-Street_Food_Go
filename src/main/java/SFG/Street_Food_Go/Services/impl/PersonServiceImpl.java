package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.PersonType;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Services.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    public PersonServiceImpl(PersonRepository personRepository){this.personRepository = personRepository;}

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public boolean personExists(Person person){
        boolean exists1 = false;
        boolean exists2 = false;

        exists1 = personRepository.existsByName(person.getName());
        exists2 = personRepository.existsByPasswordHash(person.getPasswordHash());
        System.out.println( exists1 + " " + exists2 );
        return exists1 &&  exists2;
    }

    @Override
    public Person createPerson(Person person) {


        return personRepository.save(person);
    }
}
