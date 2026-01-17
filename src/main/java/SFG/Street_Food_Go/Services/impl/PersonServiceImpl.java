package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.PersonType;
import SFG.Street_Food_Go.Port.PhoneNumberService;
import SFG.Street_Food_Go.Port.SmsNotificationPort;
import SFG.Street_Food_Go.Port.impl.model.PhoneNumberValidationResult;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Services.PersonService;
import SFG.Street_Food_Go.Services.models.PersonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;
    private final SmsNotificationPort smsNotificationPort;
    private PasswordEncoder passwordEncoder;
    private PhoneNumberService phoneNumberService;
    public PersonServiceImpl(PersonRepository personRepository,
                             PasswordEncoder passwordEncoder,
                             SmsNotificationPort smsNotificationPort,
                             PhoneNumberService phoneNumberService) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.smsNotificationPort = smsNotificationPort;
        this.phoneNumberService = phoneNumberService;
    }

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

    @Override
    public boolean personExistsById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {
            return true;
        }
        return false;
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
        if(usernameExists(person)){
            return new PersonResult(false, "Username already exists.Change To something else");
        }
        if(emailExists(person)){
            return new PersonResult(false, "Email Address already exists.Change To something else");
        }
        /*
          Check If The user Provided street NO in his address
         */
        String givenAddress = person.getAddress();
        if(!isValidAddress(givenAddress)){
            return new PersonResult(false, "Address[ '"+  givenAddress+ "' ] "+ "is not valid. You Should Add Both Name and Number Of The Address");
        }

        /*
        If phone number exists. Checking it by calling an external service.
         */
        PhoneNumberValidationResult result = phoneNumberService.validatePhoneNumber(person.getPhoneNumber());
        if(!result.valid()){
            return new PersonResult(false, "Only digits are allowed. Maximum length is 10 digits.");
        }

        person.setPhoneNumber(result.e164());

        String raw_pass = person.getPasswordHash();
        person.setPasswordHash(passwordEncoder.encode(raw_pass));
        /*
        Here Also to add the push notific after person saved
         */
        Person personSaved = personRepository.save(person);
        if (personSaved != null) {
            String content = String.format("You have successfully registered for StreetFoodGo app.To Login Use Your First Name(%s) And Your Password.", personSaved.getName());
            try {
                this.smsNotificationPort.sendSms(personSaved.getPhoneNumber(), content);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return new PersonResult(true, null);
        }
        return new PersonResult(false, "Unexpected Error");
    }

    public static boolean isValidAddress(String address){
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

    private static boolean isValidPhoneNumber(String phoneNumber){
        if(!phoneNumber.startsWith("+30")){
            return false;
        }
        return true;
    }
}
