package SFG.Street_Food_Go.Rest.Mapper;

import SFG.Street_Food_Go.Entities.Person;
import SFG.Street_Food_Go.Entities.PersonType;
import SFG.Street_Food_Go.Rest.DTO.RegisterDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PersonMapper {

    public Person registerDtoToPerson(RegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        Person person = new Person();

        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setEmailAddress(dto.getEmail());
        person.setPhoneNumber(dto.getPhone());
        person.setAddress(dto.getAddress());
        person.setCreatedAt(Instant.now());

        person.setPasswordHash(dto.getPassword());

        if (dto.getRole() != null && dto.getRole().equalsIgnoreCase("OWNER")) {
            person.setPersonType(PersonType.OWNER);
        } else {
            person.setPersonType(PersonType.CUSTOMER);
        }

        return person;
    }
}