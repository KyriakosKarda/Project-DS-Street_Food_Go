package SFG.Street_Food_Go.Repository;
import SFG.Street_Food_Go.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientId(String clientId);
}