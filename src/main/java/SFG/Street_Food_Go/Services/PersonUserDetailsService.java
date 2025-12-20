package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Repository.PersonRepository;
import SFG.Street_Food_Go.Services.models.PersonDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonUserDetailsService implements UserDetailsService {

    private PersonRepository personRepository;

    public PersonUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByName(username);
        if(person == null){
            throw new UsernameNotFoundException("User with name: "+ username+" not found");
        }
        System.err.println("ok works");
        return new PersonDetails(person);
    }
}
