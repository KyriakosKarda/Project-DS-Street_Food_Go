package SFG.Street_Food_Go.Repository;

import SFG.Street_Food_Go.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    boolean existsByName(String name);
    boolean findByPasswordHash(String passwordHash);

    Person findByName(String name);
    boolean existsByPasswordHash(String passwordHash);

    Person getPersonById(Long id);
}
