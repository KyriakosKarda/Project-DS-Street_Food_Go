package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.PersonType;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.models.PersonResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;


    private PasswordEncoder passwordEncoder;
    public PersonServiceImpl(PersonRepository personRepository,PasswordEncoder passwordEncoder ){this.personRepository = personRepository; this.passwordEncoder = passwordEncoder;}

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
    public Person getPersonById(Long id) {
        return personRepository.getPersonById(id);
    }

    private boolean usernameExists(Person person){
        String username = person.getName();
        List<Person> persons = personRepository.findAll();
        for (Person p : persons){
            if(p.getName().equals(username)){
                return true;
            }
        }
        return false;
    }

    private boolean emailExists(Person person){
        String emailAddress = person.getEmailAddress();
        List<Person> persons = personRepository.findAll();
        for (Person p : persons){
            if(p.getEmailAddress().equals(emailAddress)){
                return true;
            }
        }
        return false;
    }

    @Override
    public PersonResult createPerson(Person person) {
        if(usernameExists(person) && emailExists(person)){
            return new PersonResult(false, "Username/Email already exists Change then To something else");
        }
        /*
          Check If The user Provided street NO in his address
         */
        String givenAddress = person.getAddress();
        if(!isValidAddress(givenAddress)){
            return new PersonResult(false, "Address[ '"+  givenAddress+ "' ] "+ "is not valid. You Should Add Both Name and Number Of The Address");
        }


        String raw_pass = person.getPasswordHash();
        person.setPasswordHash(passwordEncoder.encode(raw_pass));
        System.err.println("Before encode: " + raw_pass);
        System.err.println("After encode: " + person.getPasswordHash());
        /*
        Here Also to add the push notific after person saved
         */
        Person personSaved = personRepository.save(person);
        if (personSaved != null) {
            return new PersonResult(true, null);
        }
        return new PersonResult(false, "Unexpected Error");
    }

    private boolean isValidAddress(String address){
        // koita gia to andress an exei noumera kai grammata
        // business logic
        if(address == null || address.isBlank()){
            return false;
        }
        boolean hasNumbers = false;
        boolean hasLetters = false;
        for(int i = 0; i < address.length(); i++){
            char ch = address.charAt(i);
            if(Character.isDigit(ch)){
                hasNumbers = true;
            }
            if(Character.isLetter(ch)){
                hasLetters = true;
            }
        }
        System.out.println(hasNumbers + " " + hasLetters);
        return hasNumbers && hasLetters;
    }
}
