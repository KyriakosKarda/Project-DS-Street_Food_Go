package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.PersonType;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.models.PersonResult;
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
    public PersonResult createPerson(Person person) {

        /*
          Check If The user Provided street NO in his address
         */
        String givenAddress = person.getAddress();
        if(!isValidAddress(givenAddress)){
            return new PersonResult(false, "Address[ '"+  givenAddress+ "' ] "+ "is not valid. You Should Add Both Name and Number Of The Address");
        }

        /*
        We Have to Convert Plain Text Password To Encrypted one
         */
        /*
        Here Also to add the push notific after person saved
         */
        Person personSaved = personRepository.save(person);
        if (personSaved != null) {
            return new PersonResult(true, null);
        }
        return new PersonResult(false, "Unexpected Error");
    }
    //
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
